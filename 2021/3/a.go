package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
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

	var oneCount [BitStrLength]int
	var zeroCount [BitStrLength]int

	for scanner.Scan() {
		line := scanner.Text()

		for i, c := range line {
			if c == '1' {
				oneCount[i]++
			} else {
				zeroCount[i]++
			}
		}
	}

	gammaRate := 0
	mask := 0
	for i := 0; i < BitStrLength; i++ {
		gammaRate <<= 1
		mask <<= 1
		mask |= 1
		if oneCount[i] > zeroCount[i] {
			gammaRate |= 1
		}
	}

	// TIL: Go doesn't have ~, so we need to do mask ^ v to achieve the same behaviour (i.e. 0011 ^ 1111 = 1100) :(
	epsilonRate := gammaRate ^ mask

	fmt.Println(gammaRate * epsilonRate)
}
