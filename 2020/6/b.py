#!/usr/bin/python3

with open("input") as f:
    lines = [line.strip() for line in f]
lines.append("")

yes_dict = {}
count = 0
total_in_group = 0
for line in lines:
    if line != "":
        total_in_group += 1
        for c in line.strip():
            if c not in yes_dict:
                yes_dict[c] = []

            yes_dict[c].append(1)
    else:
        for v in yes_dict.values():
            if sum(v) == total_in_group:
                count += 1
        yes_dict = {}
        total_in_group = 0

print(count)
