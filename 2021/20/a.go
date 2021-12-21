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
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	scanner.Scan()
	imageEnhancementAlgorithm := scanner.Text()

	scanner.Scan()

	var inputImage [][]rune

	for scanner.Scan() {
		line := scanner.Text()

		var row []rune
		for _, r := range line {
			row = append(row, r)
		}

		inputImage = append(inputImage, row)
	}

	for t := 0; t < 2; t++ {
		var nextImage [][]rune

		m := len(inputImage)
		n := len(inputImage[0])
		offset := 3
		for r := -offset; r < m+offset; r++ {
			var row []rune
			for c := -offset; c < n+offset; c++ {
				bn := 0
				isValid := false
				for dy := -1; dy <= 1; dy++ {
					for dx := -1; dx <= 1; dx++ {
						rr := r + dy
						cc := c + dx

						bn <<= 1
						if rr >= 0 && rr < m && cc >= 0 && cc < n && inputImage[rr][cc] == '#' {
							bn |= 1

							if t&1 == 0 {
								isValid = true
							}
						} else if t&1 == 1 && rr >= 0 && rr < m && cc >= 0 && cc < n && inputImage[rr][cc] == '.' {
							isValid = true
						}
					}
				}

				if isValid {
					row = append(row, rune(imageEnhancementAlgorithm[bn]))
				} else {
					if t&1 == 0 {
						row = append(row, '#')
					} else {
						row = append(row, '.')
					}
				}
			}

			nextImage = append(nextImage, row)
		}

		inputImage = nextImage
	}

	count := 0
	for _, row := range inputImage {
		for _, c := range row {
			if c == '#' {
				count++
			}
		}
	}

	fmt.Println(count)
}

func drawImage(image [][]rune) {
	for _, row := range image {
		for _, r := range row {
			fmt.Print(string(r))
		}
		fmt.Println()
	}
}
