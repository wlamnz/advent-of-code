package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
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

	horizontal := 0
	depth := 0
	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.Split(line, " ")
		command := parts[0]
		units, _ := strconv.Atoi(parts[1])

		switch command {
		case "forward":
			horizontal += units
		case "up":
			depth -= units
		case "down":
			depth += units
		}
	}

	fmt.Println(horizontal * depth)
}
