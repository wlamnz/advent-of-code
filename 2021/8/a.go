package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strings"
)

const (
	OneSegmentLength   = 2
	FourSegmentLength  = 4
	SevenSegmentLength = 3
	EightSegmentLength = 7
)

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	count := 0
	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.Split(line, " | ")
		fourDigitOutput := strings.Split(parts[1], " ")

		for _, fdo := range fourDigitOutput {
			if l := len(fdo); l == OneSegmentLength || l == FourSegmentLength || l == SevenSegmentLength || l == EightSegmentLength {
				count++
			}
		}
	}

	fmt.Println(count)
}
