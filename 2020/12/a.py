#!/usr/bin/python3

# N, E, S, W
dy = [1, 0, -1, 0]
dx = [0, 1, 0, -1]
dir_index = 1

y = 0
x = 0

with open("input") as f:
    for line in f:
        line = line.strip()
        action = line[0]
        val = int(line[1:])

        if action == "N":
            y += val
        elif action == "S":
            y -= val
        elif action == "E":
            x += val
        elif action == "W":
            x -= val
        elif action == "L":
            dir_index = (dir_index - (val // 90)) % 4
        elif action == "R":
            dir_index = (dir_index + (val // 90)) % 4
        elif action == "F":
            y += dy[dir_index] * val
            x += dx[dir_index] * val

print(abs(y) + abs(x))
