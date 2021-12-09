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
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	heightMap := make([][]int, 0)

	for scanner.Scan() {
		line := scanner.Text()

		var row []int
		for _, c := range line {
			row = append(row, int(c)-'0')
		}

		heightMap = append(heightMap, row)
	}

	sum := 0
	for r := 0; r < len(heightMap); r++ {
		for c := 0; c < len(heightMap[0]); c++ {
			h := heightMap[r][c]
			lowest := true
			for dy := -1; dy <= 1; dy++ {

			check:
				for dx := -1; dx <= 1; dx++ {
					if dy == 0 && dx != 0 || dy != 0 && dx == 0 {
						new_r := r + dy
						new_c := c + dx
						if new_r >= 0 && new_r < len(heightMap) && new_c >= 0 && new_c < len(heightMap[0]) && h >= heightMap[new_r][new_c] {
							lowest = false
							break check
						}
					}
				}
			}

			if lowest {
				risk_level := h + 1
				sum += risk_level
			}
		}
	}

	fmt.Println(sum)
}
