#!/usr/bin/python3

with open("input") as f:
    G = [line.strip() for line in f]

r = len(G)
c = len(G[0])

def count(right, down):
    trees = 0
    cc = 0
    for rr in range(down, r, down):
        cc += right
        if G[rr][cc % c] == "#":
            trees += 1
   
    return trees

print(count(1, 1) * count(3, 1) * count(5, 1) * count(7, 1) * count(1, 2))
