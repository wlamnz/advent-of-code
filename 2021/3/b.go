package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

const BitStrLength int = 12

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	var oxygenGeneratorRatingSlice []int
	var co2ScrubberRatingSlice []int

	for scanner.Scan() {
		line := scanner.Text()
		v, _ := strconv.ParseInt(line, 2, 32)
		oxygenGeneratorRatingSlice = append(oxygenGeneratorRatingSlice, int(v))
		co2ScrubberRatingSlice = append(co2ScrubberRatingSlice, int(v))
	}

	for i := 0; i < BitStrLength; i++ {
		bitPos := 1 << (BitStrLength - (1 + i))

		oxygenGeneratorRatingSlice = filter(oxygenGeneratorRatingSlice, bitPos, true)
		co2ScrubberRatingSlice = filter(co2ScrubberRatingSlice, bitPos, false)

	}

	fmt.Println(oxygenGeneratorRatingSlice[0] * co2ScrubberRatingSlice[0])
}

func filter(slice []int, bitPos int, keepMostCommon bool) []int {
	if len(slice) == 1 {
		return slice
	}

	var oneSlice []int
	var zeroSlice []int
	for _, val := range slice {
		if val&bitPos > 0 {
			oneSlice = append(oneSlice, val)
		} else {
			zeroSlice = append(zeroSlice, val)
		}
	}

	if keepMostCommon {
		if len(oneSlice) >= len(zeroSlice) {
			return oneSlice
		} else {
			return zeroSlice
		}
	} else {
		if len(oneSlice) < len(zeroSlice) {
			return oneSlice
		} else {
			return zeroSlice
		}
	}

}
