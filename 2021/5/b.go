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

		var xValues []int
		var yValues []int

		if x1 < x2 {
			for x := x1; x <= x2; x++ {
				xValues = append(xValues, x)
			}
		} else {
			for x := x1; x >= x2; x-- {
				xValues = append(xValues, x)
			}
		}

		if y1 < y2 {
			for y := y1; y <= y2; y++ {
				yValues = append(yValues, y)
			}
		} else {
			for y := y1; y >= y2; y-- {
				yValues = append(yValues, y)
			}
		}

		if len(xValues) == 1 {
			// x1 == x2
			for i := 0; i < len(yValues); i++ {
				grid[Point{xValues[0], yValues[i]}]++
			}
		} else if len(yValues) == 1 {
			// y1 == y2
			for i := 0; i < len(xValues); i++ {
				grid[Point{xValues[i], yValues[0]}]++
			}
		} else {
			// Diagonal
			for i := 0; i < len(xValues); i++ {
				grid[Point{xValues[i], yValues[i]}]++
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
