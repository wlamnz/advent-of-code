package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

const (
	VersionLength      = 3
	TypeLength         = 3
	GroupLength        = 5
	LiteralValue       = 4
	LengthTypeIdLength = 1
)

type Packet struct {
	version      int
	packetType   int
	literalValue int
	subPackets   []Packet
}

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	scanner.Scan()
	hexPacket := scanner.Text()

	binaryPacket := hexToBinary(hexPacket)
	packet, _ := readPacket(binaryPacket, 0)

	fmt.Println(sumVersion(packet))
}

func sumVersion(packet Packet) int {
	versionSum := packet.version
	for _, sp := range packet.subPackets {
		versionSum += sumVersion(sp)
	}

	return versionSum
}

func readPacket(binaryPacket string, pos int) (Packet, int) {
	v, pos := readVersion(binaryPacket, pos)
	t, pos := readType(binaryPacket, pos)
	newPacket := Packet{version: v, packetType: t}

	if t == LiteralValue {
		lv, newPos := readLiteralValue(binaryPacket, pos)
		pos = newPos
		newPacket.literalValue = lv
	} else {
		lt, newPos := readLengthTypeId(binaryPacket, pos)
		pos = newPos

		if lt == 0 {
			// Length of sub-packets is represented in the next 15 bits

			subPacketsLength, newPos := readIntVal(binaryPacket, pos, pos+15)
			pos = newPos
			subPacketsEndLength := subPacketsLength + pos

			for pos < subPacketsEndLength {
				subPacket, newPos := readPacket(binaryPacket, pos)
				pos = newPos
				newPacket.subPackets = append(newPacket.subPackets, subPacket)
			}
		} else if lt == 1 {
			// Total number of sub-packets is represented in the next 11 bits
			totalSubPackets, newPos := readIntVal(binaryPacket, pos, pos+11)
			pos = newPos

			for i := 0; i < totalSubPackets; i++ {
				subPacket, newPos := readPacket(binaryPacket, pos)
				pos = newPos
				newPacket.subPackets = append(newPacket.subPackets, subPacket)
			}
		}
	}

	return newPacket, pos
}

func hexToBinary(hexPacket string) string {
	binaryPacket := ""

	for _, c := range hexPacket {
		switch c {
		case '0':
			binaryPacket += "0000"
		case '1':
			binaryPacket += "0001"
		case '2':
			binaryPacket += "0010"
		case '3':
			binaryPacket += "0011"
		case '4':
			binaryPacket += "0100"
		case '5':
			binaryPacket += "0101"
		case '6':
			binaryPacket += "0110"
		case '7':
			binaryPacket += "0111"
		case '8':
			binaryPacket += "1000"
		case '9':
			binaryPacket += "1001"
		case 'A':
			binaryPacket += "1010"
		case 'B':
			binaryPacket += "1011"
		case 'C':
			binaryPacket += "1100"
		case 'D':
			binaryPacket += "1101"
		case 'E':
			binaryPacket += "1110"
		case 'F':
			binaryPacket += "1111"
		}
	}

	return binaryPacket
}

func readVersion(binaryPacket string, pos int) (int, int) {
	endLength := pos + VersionLength
	return readIntVal(binaryPacket, pos, endLength)
}

func readType(binaryPacket string, pos int) (int, int) {
	endLength := pos + TypeLength
	return readIntVal(binaryPacket, pos, endLength)
}

func readLengthTypeId(binaryPacket string, pos int) (int, int) {
	endLength := pos + LengthTypeIdLength
	return readIntVal(binaryPacket, pos, endLength)
}

func readLiteralValue(binaryPacket string, pos int) (int, int) {
	binaryNumber := 0

	for {
		n, newPos, isLast := readGroup(binaryPacket, pos)
		pos = newPos

		binaryNumber |= n

		if isLast {
			break
		} else {
			binaryNumber <<= 4
		}
	}

	return binaryNumber, pos
}

func readGroup(binaryPacket string, pos int) (int, int, bool) {
	endLength := pos + GroupLength
	lastGroup := binaryPacket[pos] == '0'
	v, _ := readIntVal(binaryPacket, pos+1, endLength)
	return v, endLength, lastGroup
}

func readIntVal(binaryPacket string, start, finish int) (int, int) {
	v := 0
	for i := start; i < finish; i++ {
		v <<= 1
		v |= int(binaryPacket[i] - '0')
	}

	return v, finish
}
