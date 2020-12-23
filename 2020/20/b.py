#!/usr/bin/python3
from math import sqrt
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

def get_top_left_corner(corner_id):
    # Get a top-left oriented tile based on a corner id
    for tile1 in gen_tile(tiles[corner_id]):
        can_link_right = False
        can_link_bottom = False
        for tid, tile2 in tiles.items():
            if tid != corner_id:
                for tile2_o in gen_tile(tile2):
                    can_link_right = can_link_right or check_right(tile1, tile2_o)
                    can_link_bottom = can_link_bottom or check_bottom(tile1, tile2_o)

                    if can_link_right and can_link_bottom:
                        return tile1
    return None

corner_id = next(iter(corners))
top_left_corner = get_top_left_corner(corner_id)

S = int(sqrt(len(tiles)))
board = [[None for _ in range(S)] for _ in range(S)]
board[0][0] = top_left_corner

id_board = [[None for _ in range(S)] for _ in range(S)]
id_board[0][0] = corner_id

used = set()
used.add(corner_id)

# Solve left-to-right, top-to-bottom
for r in range(S):
    for c in range(S - 1): 
        found = False
        if r > 0 and c == 0:
            above = board[r - 1][c]
            for tid, tile in tiles.items():
                if found:
                    break
                if tid not in used:
                    for tile2 in gen_tile(tile):
                        if check_bottom(above, tile2):
                            found = True
                            used.add(tid)
                            board[r][c] = tile2
                            id_board[r][c] = tid 
                            break
        found = False
        left = board[r][c]
        for tid, tile in tiles.items():
            if found:
                break
            if tid not in used:
                for tile2 in gen_tile(tile):
                    if check_right(left, tile2):
                        found = True
                        used.add(tid)
                        board[r][c + 1] = tile2
                        id_board[r][c + 1] = tid 
                        break

TILE_SIZE_WITHOUT_BORDERS = TILE_SIZE - 2
image = [["." for _ in range(TILE_SIZE_WITHOUT_BORDERS * S)] for _ in range(TILE_SIZE_WITHOUT_BORDERS * S)] 

# Create the image which doesn't contain the borders
for r in range(S): 
    for c in range(S):
        row_offset =  TILE_SIZE_WITHOUT_BORDERS * r
        col_offset =  TILE_SIZE_WITHOUT_BORDERS * c
        for i in range(row_offset, row_offset + TILE_SIZE_WITHOUT_BORDERS): 
            for j in range(col_offset, col_offset + TILE_SIZE_WITHOUT_BORDERS): 
                image[i][j] = board[r][c][(i + 1) - row_offset][(j + 1) - col_offset]

sea_monster = ["                  # ", "#    ##    ##    ###", " #  #  #  #  #  #   "]
smH = len(sea_monster)
smW = len(sea_monster[0])
monster_count = 0

for image_o in gen_tile(image):
    for r in range(len(image_o) - smH):
        for c in range(len(image_o[0]) - smW): 
            found = True
            for rr in range(smH):
                if not found:
                    break
                for cc in range(smW):
                    if sea_monster[rr][cc] == "#" and image_o[r + rr][c + cc] != "#":
                        found = False
                        break
            if found:
                monster_count += 1
    if monster_count > 0:
        # Can stop because we found the orientation where a monster appears
        break
    
monster_hash_count = sum([s.count("#") for s in sea_monster])
image_hash_count = sum(["".join(row).count("#") for row in image])
print(image_hash_count - monster_count * monster_hash_count)
