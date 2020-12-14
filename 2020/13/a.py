#!/usr/bin/python3

with open("input") as f:
    earliest = int(f.readline().strip())
    ids = f.readline().strip().split(',')
    ids = list(filter(lambda v : v != "x", ids))

closest = 999999999 
best_id = None
for bid in ids:
    bid = int(bid)
    r = earliest % bid

    if r != 0:
        r = bid - r
    
    if r < closest:
        closest = r
        best_id = bid

print(closest * best_id)
