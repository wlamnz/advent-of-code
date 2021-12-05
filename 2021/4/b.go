package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

const BoardSize = 5

type Board struct {
	numbers  [BoardSize][BoardSize]int
	marked   [BoardSize][BoardSize]bool
	finished bool
}

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	scanner.Scan()
	line := scanner.Text()
	var numbers []int

	for _, v := range strings.Split(line, ",") {
		n, _ := strconv.Atoi(v)
		numbers = append(numbers, n)
	}

	var boards []*Board

	for scanner.Scan() {
		line := scanner.Text()

		if line == "" {
			boards = append(boards, getBoard(scanner))
		}
	}

	winners := 0
	for _, n := range numbers {
		for _, board := range boards {
			if board.finished {
				continue
			}

			board.mark(n)
			if board.bingo() {
				board.finished = true
				winners++
				if winners == len(boards) {
					fmt.Println(board.unmarkedSum() * n)
					os.Exit(0)
				}
			}
		}
	}
}

func getBoard(scanner *bufio.Scanner) *Board {
	var board Board
	for r := 0; r < BoardSize; r++ {
		scanner.Scan()
		line := scanner.Text()
		for c, v := range strings.Fields(line) {
			n, _ := strconv.Atoi(v)
			board.numbers[r][c] = n
		}
	}

	return &board
}

func (b *Board) mark(n int) {
	for r := 0; r < BoardSize; r++ {
		for c := 0; c < BoardSize; c++ {
			if b.numbers[r][c] == n {
				b.marked[r][c] = true
			}
		}
	}
}

func (b Board) unmarkedSum() int {
	sum := 0
	for r := 0; r < BoardSize; r++ {
		for c := 0; c < BoardSize; c++ {
			if !b.marked[r][c] {
				sum += b.numbers[r][c]
			}
		}
	}

	return sum
}

func (b Board) bingo() bool {
	for r := 0; r < BoardSize; r++ {
		bingo := true
		for c := 0; c < BoardSize; c++ {
			if !b.marked[r][c] {
				bingo = false
				break
			}
		}

		if bingo {
			return true
		}
	}

	for c := 0; c < BoardSize; c++ {
		bingo := true
		for r := 0; r < BoardSize; r++ {
			if !b.marked[r][c] {
				bingo = false
				break
			}
		}

		if bingo {
			return true
		}
	}

	return false
}
