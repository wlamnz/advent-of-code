package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strings"
)

const (
	Days                 = 256
	ExistingFishDaysLeft = 7
	NewFishDaysLeft      = 9
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

	var sum int64 = int64(len(timers))
	dp := make(map[int]int64)

	for _, t := range timers {
		// Number of days before a new fish is added for the current timer (e.g. if t = 1, new fish is added two days later)
		daysLeft := t + 1
		sum += fillTimers(daysLeft, dp)
	}

	fmt.Println(sum)
}

func fillTimers(daysLeft int, dp map[int]int64) int64 {
	if val, ok := dp[daysLeft]; ok {
		return val
	}

	count := int64(0)
	for i := daysLeft; i <= Days; i += ExistingFishDaysLeft {
		count++
		// The new fish will add another new fish every 9 days
		count += fillTimers(i+NewFishDaysLeft, dp)
	}

	dp[daysLeft] = count

	return count
}
