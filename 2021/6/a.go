package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strings"
)

const (
	Days              = 80
	ExistingFishTimer = 6
	NewFishTimer      = 8
)

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	scanner.Scan()
	line := scanner.Text()
	var timers []int

	for _, v := range strings.Split(line, ",") {
		timers = append(timers, int(v[0])-'0')
	}

	for i := 0; i < Days; i++ {
		var next []int
		newFishCount := 0
		for _, t := range timers {
			if t == 0 {
				newFishCount++
				next = append(next, ExistingFishTimer)
			} else {
				next = append(next, t-1)
			}
		}

		for j := 0; j < newFishCount; j++ {
			next = append(next, NewFishTimer)
		}

		timers = next
	}

	fmt.Println(len(timers))
}
