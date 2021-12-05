package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	prefixSum := []int{0}

	count := 0
	for i := 1; scanner.Scan(); i++ {
		line := scanner.Text()
		value, _ := strconv.Atoi(line)

		prefixSum = append(prefixSum, value+prefixSum[len(prefixSum)-1])

		if i > 3 && prefixSum[i]-prefixSum[i-3] > prefixSum[i-1]-prefixSum[i-4] {
			count++
		}
	}

	fmt.Println(count)
}
