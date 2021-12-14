package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strings"
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
	polymerTemplate := scanner.Text()
	pairCountMap := make(map[string]int)

	for i := 1; i < len(polymerTemplate); i++ {
		ss := polymerTemplate[i-1 : i+1]
		pairCountMap[ss]++
	}

	rules := make(map[string]string, 0)

	for scanner.Scan() {
		line := scanner.Text()

		if line == "" {
			continue
		}

		parts := strings.Split(line, " -> ")
		rules[parts[0]] = parts[1]
	}

	for t := 1; t <= 40; t++ {
		next := make(map[string]int)
		for k, v := range pairCountMap {
			v2, ok := rules[k]

			if ok {
				next[string(k[0])+v2] += v
				next[v2+string(k[1])] += v
			} else {
				fmt.Println("No insertion rule for ", k)
			}
		}

		pairCountMap = next
	}

	countMap := make(map[rune]int)

	// Count the first char of the polymer template since we're counting the second char of every pair.
	// Note: first char of polymer template is an invariant.
	countMap[rune(polymerTemplate[0])]++

	for k, v := range pairCountMap {
		countMap[rune(k[1])] += v
	}

	min := 1000000000000000
	max := 0

	for _, v := range countMap {
		if min > v {
			min = v
		}

		if max < v {
			max = v
		}
	}

	fmt.Println(max - min)
}
