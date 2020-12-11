#!/usr/bin/python3
from copy import deepcopy

with open("input") as f:
    G = [list(line.strip()) for line in f]

R = len(G)
C = len(G[0])

def get_adj_count(r, c):
    count = 0

    for dy in [-1, 0, 1]:
        for dx in [-1, 0, 1]:
            if dy == 0 and dx == 0:
                continue

            rr = r + dy
            cc = c + dx

            if 0 <= rr < R and 0 <= cc < C and G[rr][cc] == "#":
                count += 1

    return count

stabilized = False
while not stabilized:
    G_next = deepcopy(G)
    change = False

    for r in range(R):
        for c in range(C):
            if G[r][c] =="L" and get_adj_count(r, c) == 0:
                G_next[r][c] = "#"
                change = True
            elif G[r][c] == "#" and get_adj_count(r, c) >= 4:
                G_next[r][c] = "L"
                change = True

    stabilized = not change
    G = G_next

occupied_count = 0

for r in range(R):
    for c in range(C):
        if G[r][c] == "#":
            occupied_count += 1

print(occupied_count)
