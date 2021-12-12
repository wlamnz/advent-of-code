package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strings"
)

type Vertex struct {
	id        string
	isBigCave bool
}

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	graph := make(map[Vertex][]Vertex)

	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.Split(line, "-")
		v1Id := parts[0]
		v2Id := parts[1]

		vertex1 := Vertex{v1Id, v1Id[0] >= 'A' && v1Id[0] <= 'Z'}
		vertex2 := Vertex{v2Id, v2Id[0] >= 'A' && v2Id[0] <= 'Z'}

		graph[vertex1] = append(graph[vertex1], vertex2)
		graph[vertex2] = append(graph[vertex2], vertex1)
	}

	visited := make(map[Vertex]int)
	count := dfs(graph, Vertex{"start", false}, visited)
	fmt.Println(count)
}

func dfs(graph map[Vertex][]Vertex, curVertex Vertex, visited map[Vertex]int) int {
	if curVertex.id == "end" {
		return 1
	}

	count := 0

	visitedMoreThanOnceCount := 0

	for k, v := range visited {
		if !k.isBigCave && k.id != "start" && k.id != "end" {

			if v == 2 {
				visitedMoreThanOnceCount++
			}
		}
	}

	for _, vertex := range graph[curVertex] {
		if vertex.id == "start" {
			continue
		}

		timesVisited := visited[vertex]

		if vertex.isBigCave || (visitedMoreThanOnceCount == 1 && timesVisited == 0) || (visitedMoreThanOnceCount == 0 && timesVisited < 2) {
			visited[vertex]++
			count += dfs(graph, vertex, visited)
			visited[vertex]--
		}

	}

	return count
}
