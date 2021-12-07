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
	var horizontalPositions []int
	max := 0

	for _, v := range strings.Split(line, ",") {
		hp, _ := strconv.Atoi(v)
		if max < hp {
			max = hp
		}
		horizontalPositions = append(horizontalPositions, hp)
	}

	best := 1000000000
	for hp := 0; hp <= max; hp++ {
		totalFuel := 0
		for _, v := range horizontalPositions {
			fuel := v - hp
			if fuel < 0 {
				fuel *= -1
			}

			fuel = (fuel * (fuel + 1)) / 2

			totalFuel += fuel
		}

		if totalFuel < best {
			best = totalFuel
		}

	}

	fmt.Println(best)
}
