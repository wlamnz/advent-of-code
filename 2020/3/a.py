#!/usr/bin/python3

with open("input") as f:
    G = [line.strip() for line in f]

r = len(G)
c = len(G[0])

trees = 0
cc = 0
for rr in range(1, r):
    cc += 3
    if G[rr][cc % c] == "#":
        trees += 1

print(trees)
