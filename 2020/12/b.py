#!/usr/bin/python3

# N, E, S, W
dy = [1, 0, -1, 0]
dx = [0, 1, 0, -1]
dir_index = 1

y = 0
x = 0

wy = 1
wx = 10

rotation_matrix = [[0, 1], [-1, 0]]

def rotate_ccw(oy, ox, times):
    for i in range(times):
        new_oy = oy * rotation_matrix[0][0] + ox * rotation_matrix[0][1]
        new_ox = oy * rotation_matrix[1][0] + ox * rotation_matrix[1][1]
        oy = new_oy
        ox = new_ox

    return (oy, ox)

with open("input") as f:
    for line in f:
        line = line.strip()
        action = line[0]
        val = int(line[1:])

        if action == "N":
            wy += val
        elif action == "S":
            wy -= val
        elif action == "E":
            wx += val
        elif action == "W":
            wx -= val
        elif action == "L":
            p = rotate_ccw(wy, wx, val // 90)
            wy = p[0]
            wx = p[1]
        elif action == "R":
            p = rotate_ccw(wy, wx, (val // 90) * 3)
            wy = p[0]
            wx = p[1]
        elif action == "F":
            y += val * wy
            x += val * wx

print(abs(y) + abs(x))
