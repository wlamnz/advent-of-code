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
	scanner.Scan()
	line := scanner.Text()

	parts := strings.Split(line, " ")
	xRange := strings.Split(parts[2][2:len(parts[2])-1], "..")
	yRange := strings.Split(parts[3][2:], "..")

	startX, _ := strconv.Atoi(xRange[0])
	endX, _ := strconv.Atoi(xRange[1])
	startY, _ := strconv.Atoi(yRange[0])
	endY, _ := strconv.Atoi(yRange[1])

	maxY := -100000
	for vx := 1; vx <= endX; vx++ {
		for vy := startY; vy <= 1000; vy++ {
			x := 0
			y := 0
			ix := vx
			iy := vy

			tmpMaxY := -100000
			for s := 0; s < 1000; s++ {
				x += ix
				y += iy
				tmpMaxY = max(y, tmpMaxY)

				if x >= startX && x <= endX && y >= startY && y <= endY {
					maxY = max(tmpMaxY, maxY)
					break
				} else if x > endX || y < startY {
					break
				} else {
					iy -= 1

					if ix != 0 {
						if ix > 0 {
							ix--
						} else {
							ix++
						}
					}
				}
			}

		}
	}

	fmt.Println(maxY)
}

func max(a, b int) int {
	if a > b {
		return a
	}

	return b
}
