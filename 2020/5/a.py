#!/usr/bin/python3

def get_seat_id(boarding_pass):
    ri, rj = 0, 127
    ci, cj = 0, 7

    for l in boarding_pass[:-3]:
        m = (ri + rj) // 2
        if l == "F":
            rj = m
        elif l == "B":
            ri = m + 1

    for l in boarding_pass[-3:]:
        m = (ci + cj) // 2
        if l == "L":
            cj = m
        elif l == "R":
            ci = m + 1

    return ri * 8 + ci

highest = 0
with open("input") as f:
    for line in f:
        highest = max(get_seat_id(line.strip()), highest)

print(highest)
