#!/usr/bin/python3
import re

field_ranges = {}
field_pos_count = {}

with open("input") as f:
    for line in f:
        line = line.strip()

        if not line:
            break

        lhs, rhs = line.split(": ")
        r1, r2, r3, r4 = re.split('-| or ', rhs)
        field_ranges[lhs] = (int(r1), int(r2), int(r3), int(r4))

    for key in field_ranges:
        field_pos_count[key] = [0] * len(field_ranges)

    f.readline()
    ticket = [int(num) for num in f.readline().strip().split(",")]
    f.readline()
    f.readline()

    total_valid = 0
    for line in f:
        valid = True
        nums = line.strip().split(",")
        for num in nums:
            num = int(num)

            if not any(r1 <= num <= r2 or r3 <= num <= r4 for r1, r2, r3, r4 in field_ranges.values()):
                valid = False
                break

        if valid:
            total_valid += 1

            for i, num in enumerate(nums):
                num = int(num)

                for key, (r1, r2, r3, r4) in field_ranges.items():
                    if r1 <= num <= r2 or r3 <= num <= r4:
                        field_pos_count[key][i] += 1

solution = [None] * len(field_pos_count)
seen = set()

while len(seen) != len(field_pos_count):
    for key, value in field_pos_count.items():
        if key in seen:
            continue
        
        ways = [i for i, count in enumerate(value) if solution[i] == None and count == total_valid]
        if len(ways) == 1:
            solution[ways[0]] = key
            seen.add(key)

ans = 1
for idx, w in enumerate(solution):
    if w.startswith("departure"):
        ans *= ticket[idx]

print(ans)
