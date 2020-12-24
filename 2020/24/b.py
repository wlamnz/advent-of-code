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

day = {k: v for k, v in tiles_identified.items() if v}

def get_next_state(y, x, is_black):
    black_neighbours = 0

    for dy, dx in dir_vecs.values():
        yy = y + dy
        xx = x + dx
        tup = (yy, xx)

        if tup in day:
            black_neighbours += 1

    if is_black and (black_neighbours == 0 or black_neighbours > 2):
        return False
    elif not is_black and black_neighbours == 2:
        return True

    return is_black

for i in range(100):
    next_day = {}
    for y, x in day.keys():
        # Check if white adjacency tiles need to change to black
        for dy, dx in dir_vecs.values():
            yy = y + dy
            xx = x + dx
            tup = (yy, xx)
            if tup in day:
                # Will already be accounted for in the outer y, x loop
                continue
            
            if get_next_state(yy, xx, False): 
                next_day[tup] = True

        tup = (y, x)
        if get_next_state(y, x, True): 
            next_day[tup] = True

    day = next_day

print(sum(day.values()))
