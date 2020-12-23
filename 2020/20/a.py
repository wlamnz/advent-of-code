#!/usr/bin/python3
from functools import reduce 

TILE_SIZE = 10

def rotate_cw(tile):
    return list(zip(*tile[::-1]))

def flip(tile):
    return tile[::-1]

tiles = {}

with open("input") as f:
    tile = [["." for _ in range(TILE_SIZE)] for _ in range(TILE_SIZE)]
    tid = None
    r = 0
    for line in f:
        line = line.strip()

        if not line:
            tile = [["." for _ in range(TILE_SIZE)] for _ in range(TILE_SIZE)]
            r = 0
            continue
        elif line.startswith("Tile"):
            tid = int(line[5: -1])
            tiles[tid] = tile
        else:
            for i, c in enumerate(line):
                tiles[tid][r][i] = c
            r += 1

def check_right(tile1, tile2):
    for r in range(len(tile1)):
        if tile1[r][-1] != tile2[r][0]:
            return False

    return True

def check_bottom(tile1, tile2):
    for c in range(len(tile1[0])):
        if tile1[-1][c] != tile2[0][c]:
            return False

    return True

def can_link(tile1, tile2):
    return check_right(tile1, tile2) or check_right(tile2, tile1) or check_bottom(tile1, tile2) or check_bottom(tile2, tile1) 

def gen_tile(tile):
    # Can rotate up to four times
    for i in range(4):
        tile = rotate_cw(tile)
        yield tile

    tile = flip(tile)

    for i in range(4):
        tile = rotate_cw(tile)
        yield tile

corners = set()
for tid, tile in tiles.items():
    count = 0

    for tid2, tile2 in tiles.items():
        if tid == tid2:
            continue

        for tile1 in gen_tile(tile):
            if can_link(tile1, tile2):
                count +=1
                break

    if count == 2:
        corners.add(tid)

print(reduce(lambda a, b: a * b, corners))
