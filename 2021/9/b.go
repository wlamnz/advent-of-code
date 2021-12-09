package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"sort"
)

type Coord struct {
	R, C int
}

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	heightMap := make([][]int, 0)
	visited := make([][]bool, 0)

	for scanner.Scan() {
		line := scanner.Text()

		var row []int
		var visitedRow []bool
		for _, c := range line {
			row = append(row, int(c)-'0')
			visitedRow = append(visitedRow, false)

		}

		heightMap = append(heightMap, row)
		visited = append(visited, visitedRow)
	}

	var basins []int
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
				basins = append(basins, bfs(visited, heightMap, r, c))
			}
		}
	}

	sort.Ints(basins)
	a := basins[len(basins)-1]
	b := basins[len(basins)-2]
	c := basins[len(basins)-3]
	fmt.Println(a * b * c)
}

func bfs(visited [][]bool, heightMap [][]int, startR, startC int) int {
	q := make([]Coord, 0)
	q = append(q, Coord{startR, startC})

	size := 0
	for len(q) != 0 {
		coord := q[0]
		q = q[1:]

		if !visited[coord.R][coord.C] {
			size++
			visited[coord.R][coord.C] = true

			for dy := -1; dy <= 1; dy++ {
				for dx := -1; dx <= 1; dx++ {
					if dy == 0 && dx != 0 || dy != 0 && dx == 0 {
						new_r := coord.R + dy
						new_c := coord.C + dx

						if new_r >= 0 && new_r < len(heightMap) && new_c >= 0 && new_c < len(heightMap[0]) && !visited[new_r][new_c] && heightMap[new_r][new_c] != 9 {
							q = append(q, Coord{new_r, new_c})
						}
					}
				}
			}

		}

	}

	return size
}
