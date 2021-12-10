package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	totalPoints := 0

	for scanner.Scan() {
		line := scanner.Text()

		var stack []rune

		for _, c := range line {
			if c == '(' || c == '[' || c == '{' || c == '<' {
				stack = append(stack, c)
			} else if len(stack) > 0 {
				n := len(stack) - 1
				last := stack[n]
				stack = stack[:n]

				if c == ')' && last != '(' {
					totalPoints += 3
					break
				} else if c == ']' && last != '[' {
					totalPoints += 57
					break
				} else if c == '}' && last != '{' {
					totalPoints += 1197
					break
				} else if c == '>' && last != '<' {
					totalPoints += 25137
					break
				}
			}
		}
	}

	fmt.Println(totalPoints)

}
