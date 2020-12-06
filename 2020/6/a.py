#!/usr/bin/python3

with open("input") as f:
    lines = [line.strip() for line in f]
lines.append("")

yes_set = set()
count = 0
for line in lines:
    if line != "":
        for c in line:
            yes_set.add(c)
    else:
        count += len(yes_set) 
        yes_set = set()

print(count)
