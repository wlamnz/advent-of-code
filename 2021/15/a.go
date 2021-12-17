package main

import (
	"bufio"
	"container/heap"
	"fmt"
	"log"
	"os"
)

const MAX = 1000000000

type Node struct {
	r            int
	c            int
	bestDistance int
	visited      bool
	index        int // The index of the item in the heap.
}

type PriorityQueue []*Node

func (pq PriorityQueue) Len() int { return len(pq) }

func (pq PriorityQueue) Less(i, j int) bool {
	return pq[i].bestDistance < pq[j].bestDistance
}

func (pq PriorityQueue) Swap(i, j int) {
	pq[i], pq[j] = pq[j], pq[i]
	pq[i].index = i
	pq[j].index = j
}

func (pq *PriorityQueue) Push(x interface{}) {
	n := len(*pq)
	node := x.(*Node)
	node.index = n
	*pq = append(*pq, node)
}

func (pq *PriorityQueue) Pop() interface{} {
	old := *pq
	n := len(old)
	item := old[n-1]
	old[n-1] = nil  // avoid memory leak
	item.index = -1 // for safety
	*pq = old[0 : n-1]
	return item
}

func (pq *PriorityQueue) update(node *Node, distance int) {
	node.bestDistance = distance
	heap.Fix(pq, node.index)
}

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var riskLevels [][]int

	for scanner.Scan() {
		line := scanner.Text()

		var row []int
		for c := 0; c < len(line); c++ {
			row = append(row, int(line[c]-'0'))
		}
		riskLevels = append(riskLevels, row)
	}

	m := len(riskLevels)
	n := len(riskLevels[0])

	var nodes [][]*Node

	for r := 0; r < m; r++ {
		row := make([]*Node, n)
		nodes = append(nodes, row)
	}

	for r := 0; r < m; r++ {
		for c := 0; c < n; c++ {
			nodes[r][c] = &Node{r: r, c: c, bestDistance: MAX}
		}
	}

	best := dijkstras(riskLevels, nodes, m, n)
	fmt.Println(best)
}

func dijkstras(riskLevels [][]int, nodes [][]*Node, m, n int) int {
	pq := make(PriorityQueue, 0)
	heap.Init(&pq)

	nodes[0][0].bestDistance = 0
	heap.Push(&pq, nodes[0][0])

	for pq.Len() > 0 {
		node := heap.Pop(&pq).(*Node)

		if node.visited {
			continue
		}

		if node.r == m-1 && node.c == n-1 {
			return node.bestDistance
		}

		node.visited = true

		for dy := -1; dy <= 1; dy++ {
			for dx := -1; dx <= 1; dx++ {
				if dy == 0 && dx != 0 || dy != 0 && dx == 0 {
					newR := node.r + dy
					newC := node.c + dx

					if newR >= 0 && newR < m && newC >= 0 && newC < n {
						next := nodes[newR][newC]
						distance := node.bestDistance + riskLevels[newR][newC]

						contains := false
						for _, n := range pq {
							if n == next {
								contains = true
								break
							}
						}

						if !contains {
							pq = append(pq, next)
						}

						if distance < next.bestDistance {
							pq.update(next, distance)
						}
					}
				}
			}
		}

	}

	return MAX
}
