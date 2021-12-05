package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

type Point struct {
	X, Y int
}

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	grid := make(map[Point]int)

	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.Split(line, " -> ")
		p1Parts := strings.Split(parts[0], ",")
		p2Parts := strings.Split(parts[1], ",")
		x1, _ := strconv.Atoi(p1Parts[0])
		y1, _ := strconv.Atoi(p1Parts[1])
		x2, _ := strconv.Atoi(p2Parts[0])
		y2, _ := strconv.Atoi(p2Parts[1])

		if x1 == x2 {
			if y1 < y2 {
				for y := y1; y <= y2; y++ {
					grid[Point{x1, y}]++
				}
			} else {
				for y := y2; y <= y1; y++ {
					grid[Point{x1, y}]++
				}
			}
		} else if y1 == y2 {
			if x1 < x2 {
				for x := x1; x <= x2; x++ {
					grid[Point{x, y1}]++
				}
			} else {
				for x := x2; x <= x1; x++ {
					grid[Point{x, y1}]++
				}
			}
		}
	}

	count := 0
	for _, v := range grid {
		if v >= 2 {
			count++
		}
	}

	fmt.Println(count)
}
