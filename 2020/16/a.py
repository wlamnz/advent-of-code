#!/usr/bin/python3
import re

field_ranges = {}
error_rate = 0

with open("input") as f:
    for line in f:
        line = line.strip()

        if not line:
            break

        lhs, rhs = line.split(": ")
        r1, r2, r3, r4 = re.split('-| or ', rhs)
        field_ranges[lhs] = (int(r1), int(r2), int(r3), int(r4))

    f.readline()
    ticket = [int(num) for num in f.readline().strip().split(",")]
    f.readline()
    f.readline()

    for line in f:
        for num in line.strip().split(","):
            num = int(num)

            if not any(r1 <= num <= r2 or r3 <= num <= r4 for r1, r2, r3, r4 in field_ranges.values()):
                error_rate += num

print(error_rate)
