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
	polymerTemplate := []rune(scanner.Text())

	rules := make(map[string]rune, 0)

	for scanner.Scan() {
		line := scanner.Text()

		if line == "" {
			continue
		}

		parts := strings.Split(line, " -> ")
		rules[parts[0]] = rune(parts[1][0])
	}

	for t := 1; t <= 10; t++ {
		next := []rune{polymerTemplate[0]}

		for i := 1; i < len(polymerTemplate); i++ {
			ss := polymerTemplate[i-1 : i+1]

			v, ok := rules[string(ss)]

			if ok {
				next = append(next, v)
				next = append(next, rune(ss[1]))
			} else {
				fmt.Println("No insertion rule for ", ss)
			}
		}

		polymerTemplate = next
	}

	countMap := make(map[rune]int)

	for _, r := range polymerTemplate {
		countMap[r]++
	}

	min := 1000000000
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
