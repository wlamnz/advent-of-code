package main

import (
	"bufio"
	"fmt"
	"log"
	"math"
	"os"
	"sort"
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

	sum := 0
	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.Split(line, " | ")
		uniqueSignalPattern := strings.Split(parts[0], " ")

		segmentMapping := make(map[string]int)
		lengthMapping := make(map[int][]string)

		oneSignalPattern := ""
		fourSignalPattern := ""

		for _, usp := range uniqueSignalPattern {
			s := sortStringByChars(usp)
			if l := len(s); l == OneSegmentLength {
				segmentMapping[s] = 1
				oneSignalPattern = s
			} else if l == FourSegmentLength {
				segmentMapping[s] = 4
				fourSignalPattern = s
			} else if l == SevenSegmentLength {
				segmentMapping[s] = 7
			} else if l == EightSegmentLength {
				segmentMapping[s] = 8
			} else {
				lengthMapping[l] = append(lengthMapping[l], s)
			}
		}

		for _, fiveLengthSegment := range lengthMapping[5] {
			if containsAll(fiveLengthSegment, oneSignalPattern) {
				segmentMapping[fiveLengthSegment] = 3
			} else if charDiffCount(fiveLengthSegment, fourSignalPattern) == 1 {
				segmentMapping[fiveLengthSegment] = 5
			} else {
				segmentMapping[fiveLengthSegment] = 2
			}
		}

		for _, sixLengthSegment := range lengthMapping[6] {
			if containsAll(sixLengthSegment, fourSignalPattern) {
				segmentMapping[sixLengthSegment] = 9
			} else if containsAll(sixLengthSegment, oneSignalPattern) {
				segmentMapping[sixLengthSegment] = 0
			} else {
				segmentMapping[sixLengthSegment] = 6
			}
		}

		fourDigitOutput := strings.Split(parts[1], " ")
		fourDigitOutputValue := 0

		for i, fdo := range fourDigitOutput {
			s := sortStringByChars(fdo)
			fourDigitOutputValue += segmentMapping[s] * int(math.Pow10(3-i))
		}

		sum += fourDigitOutputValue
	}

	fmt.Println(sum)
}

func sortStringByChars(s string) string {
	charStrings := strings.Split(s, "")
	sort.Strings(charStrings)
	return strings.Join(charStrings, "")
}

func containsAll(s, chars string) bool {
	for _, c := range chars {
		if !strings.ContainsRune(s, c) {
			return false
		}
	}

	return true
}

func charDiffCount(s, chars string) int {
	count := 0
	for _, c := range chars {
		if !strings.ContainsRune(s, c) {
			count++
		}
	}

	return count
}
