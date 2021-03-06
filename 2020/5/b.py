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

with open("input") as f:
    all_boarding_passes = sorted([get_seat_id(line.strip()) for line in f])

for i, bp in enumerate(all_boarding_passes, all_boarding_passes[0]):
    if i != bp:
        print(i)
        break
