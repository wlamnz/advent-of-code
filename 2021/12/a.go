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

	visited := make(map[string]bool)
	count := dfs(graph, Vertex{"start", false}, visited)
	fmt.Println(count)
}

func dfs(graph map[Vertex][]Vertex, curVertex Vertex, visited map[string]bool) int {
	if curVertex.id == "end" {
		return 1
	}

	visited[curVertex.id] = true
	count := 0

	for _, vertex := range graph[curVertex] {
		_, ok := visited[vertex.id]

		if vertex.isBigCave || !ok {
			count += dfs(graph, vertex, visited)
			delete(visited, vertex.id)
		}

	}

	return count
}
