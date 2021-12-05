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

	prev := -1
	count := 0
	for scanner.Scan() {
		line := scanner.Text()
		value, _ := strconv.Atoi(line)

		if value > prev && prev != -1 {
			count++
		}

		prev = value
	}

	fmt.Println(count)
}
