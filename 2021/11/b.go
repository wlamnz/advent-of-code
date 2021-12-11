package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

const Size int = 10

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var energyLevel [Size][Size]int

	for r := 0; r < Size; r++ {
		scanner.Scan()
		line := scanner.Text()

		for c := 0; c < Size; c++ {
			energyLevel[r][c] = int(line[c] - '0')
		}
	}

	for s := 1; ; s++ {
		var next [Size][Size]int

		for r := 0; r < Size; r++ {
			for c := 0; c < Size; c++ {
				next[r][c] = energyLevel[r][c] + 1

			}
		}

		var hasFlashed [Size][Size]bool

		flashCount := 0
		for {
			flash := false
			for r := 0; r < Size; r++ {
				for c := 0; c < Size; c++ {
					if next[r][c] > 9 && !hasFlashed[r][c] {
						flashCount++
						hasFlashed[r][c] = true
						flash = true
						for dy := -1; dy <= 1; dy++ {
							for dx := -1; dx <= 1; dx++ {
								newR := r + dy
								newC := c + dx

								if newR >= 0 && newR < Size && newC >= 0 && newC < Size {
									next[newR][newC]++
								}
							}
						}
					}
				}
			}

			if !flash {
				break
			}
		}

		for r := 0; r < Size; r++ {
			for c := 0; c < Size; c++ {
				if next[r][c] > 9 {
					next[r][c] = 0
				}
			}
		}
		energyLevel = next

		if flashCount == 100 {
			fmt.Println(s)
			break
		}
	}

}
