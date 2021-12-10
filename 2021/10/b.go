package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"sort"
)

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var allScores []int

	for scanner.Scan() {
		line := scanner.Text()

		var stack []rune
		corrupted := false

		for _, c := range line {
			if c == '(' || c == '[' || c == '{' || c == '<' {
				stack = append(stack, c)
			} else if len(stack) > 0 {
				n := len(stack) - 1
				last := stack[n]
				stack = stack[:n]

				if c == ')' && last != '(' {
					corrupted = true
					break
				} else if c == ']' && last != '[' {
					corrupted = true
					break
				} else if c == '}' && last != '{' {
					corrupted = true
					break
				} else if c == '>' && last != '<' {
					corrupted = true
					break
				}
			}
		}

		if len(stack) > 0 && !corrupted {
			score := 0
			for i := len(stack) - 1; i >= 0; i-- {
				c := stack[i]
				score *= 5
				switch c {
				case '(':
					score += 1
				case '[':
					score += 2
				case '{':
					score += 3
				case '<':
					score += 4
				}
			}

			allScores = append(allScores, score)
		}
	}

	sort.Ints(allScores)
	fmt.Println(allScores[len(allScores)/2])

}
