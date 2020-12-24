#!/usr/bin/python3

def next_dir(line):
    pos = 0
    while pos < len(line):
        if line[pos] == "s" or line[pos] == "n":
            pos += 2 
            yield line[pos - 2: pos] 
        else:
            pos += 1
            yield line[pos - 1]

dir_vecs = {"e" : (0, 2), "w" : (0, -2), "ne" : (1, 1), "nw" : (1, -1), "se" : (-1, 1), "sw" : (-1, -1)}
# Value: True == Black, False == White
tiles_identified = {}

with open("input") as f:
    for line in f:
        line = line.strip()
        y = 0
        x = 0
        for d in next_dir(line):
            y += dir_vecs[d][0]
            x += dir_vecs[d][1]
        tup = (y, x)
        if tup not in tiles_identified:
            # Put white tile
            tiles_identified[tup] = False
        # Flip tile
        tiles_identified[tup] = not tiles_identified[tup] 

print(sum(tiles_identified.values()))
