#!/usr/bin/python3

def get_pocket_dimension(W, Z, Y, X):
    pd = [[[['.' for x in range(X)] for y in range(Y)] for z in range(Z)] for w in range(W)]
    return pd

with open("input") as f:
    seed = [line.strip() for line in f]

pd = get_pocket_dimension(1, 1, len(seed), len(seed[0]))

for r in range(len(seed)):
    for c in range(len(seed[0])):
        pd[0][0][r][c] = seed[r][c]

for c in range(6):
    W = len(pd) + 2
    Z = len(pd[0]) + 2
    Y = len(pd[0][0]) + 2
    X = len(pd[0][0][0]) + 2
    next_pd = get_pocket_dimension(W, Z, Y, X)
    expanded_pd = get_pocket_dimension(W, Z, Y, X)

    for w in range(len(pd)):
        for z in range(len(pd[0])):
            for y in range(len(pd[0][0])):
                for x in range(len(pd[0][0][0])):
                    expanded_pd[w + 1][z + 1][y + 1][x + 1] = pd[w][z][y][x]

    for w in range(W):
        for z in range(Z):
            for y in range(Y):
                for x in range(X):
                    active = 0

                    for dw in [-1, 0, 1]:
                        for dz in [-1, 0, 1]:
                            for dy in [-1, 0, 1]:
                                for dx in [-1, 0, 1]:
                                    if dw == 0 and dz == 0 and dy == 0 and dx == 0:
                                        continue
                                    
                                    ww = w + dw
                                    zz = z + dz
                                    yy = y + dy
                                    xx = x + dx

                                    if 0 <= ww < W and 0 <= zz < Z and 0 <= yy < Y and 0 <= xx < X and expanded_pd[ww][zz][yy][xx] == "#":
                                        active += 1
                    
                    if expanded_pd[w][z][y][x] == "#" and (active == 2 or active == 3):
                        next_pd[w][z][y][x] = "#"
                    elif expanded_pd[w][z][y][x] == "." and active == 3:
                        next_pd[w][z][y][x] = "#"

    pd = next_pd

count = 0
for w in range(len(pd)):
    for z in range(len(pd[0])):
        for y in range(len(pd[0][0])):
            for x in range(len(pd[0][0][0])):
                if pd[w][z][y][x] == "#":
                    count += 1
print(count)
