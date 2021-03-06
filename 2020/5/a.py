#!/usr/bin/python3

def get_seat_id(boarding_pass):
    ri, rj = 0, 127
    ci, cj = 0, 7

    for l in boarding_pass:
        m1 = (ri + rj) // 2
        m2 = (ci + cj) // 2
        if l == "F":
            rj = m1
        elif l == "B":
            ri = m1 + 1
        elif l == "L":
            cj = m2
        elif l == "R":
            ci = m2 + 1

    return ri * 8 + ci

highest = 0
with open("input") as f:
    for line in f:
        highest = max(get_seat_id(line.strip()), highest)

print(highest)
