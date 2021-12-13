package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

type Coord struct {
	X, Y int
}

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	paper := make(map[Coord]bool)

	for scanner.Scan() {
		line := scanner.Text()

		if line == "" {
			break
		}

		parts := strings.Split(line, ",")
		x, _ := strconv.Atoi(parts[0])
		y, _ := strconv.Atoi(parts[1])

		paper[Coord{x, y}] = true
	}

	for scanner.Scan() {
		line := scanner.Text()

		parts := strings.Split(line, "=")
		isFoldY := parts[0][len(parts[0])-1] == 'y'
		val, _ := strconv.Atoi(parts[1])

		if isFoldY {
			paper = foldY(paper, val)
		} else {
			paper = foldX(paper, val)
		}

		fmt.Println(len(paper))
		break
	}

}

func foldY(paper map[Coord]bool, y int) map[Coord]bool {
	newPaper := make(map[Coord]bool)
	for k := range paper {
		if k.Y < y {
			newPaper[k] = true
		} else {
			newY := y - (k.Y - y)
			newPaper[Coord{k.X, newY}] = true
		}
	}

	return newPaper
}

func foldX(paper map[Coord]bool, x int) map[Coord]bool {
	newPaper := make(map[Coord]bool)
	for k := range paper {
		if k.X < x {
			newPaper[k] = true
		} else {
			newX := x - (k.X - x)
			newPaper[Coord{newX, k.Y}] = true
		}
	}

	return newPaper
}

func draw(paper map[Coord]bool) {
	maxY := 0
	maxX := 0

	for k := range paper {
		y := k.Y
		x := k.X

		if maxY < y {
			maxY = y
		}

		if maxX < x {
			maxX = x
		}
	}

	maxY++
	maxX++

	toDraw := make([][]string, maxY)

	for r := 0; r < maxY; r++ {
		toDraw[r] = make([]string, maxX)

		for c := 0; c < maxX; c++ {
			toDraw[r][c] = "."
		}
	}

	for k, v := range paper {
		if v {
			toDraw[k.Y][k.X] = "#"
		} else {
			toDraw[k.Y][k.X] = "."
		}
	}

	for r := 0; r < maxY; r++ {
		fmt.Println(toDraw[r])
	}

}
