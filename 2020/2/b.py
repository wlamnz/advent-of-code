#!/usr/bin/python3
import re

def is_valid(line):
    parts = re.split('-| |: ', line)

    i = int(parts[0])
    j = int(parts[1])
    letter = parts[2]
    password = parts[3]
    c1 = password[i - 1]
    c2 = password[j - 1]

    return c1 != c2 and (c1 == letter or c2 == letter)

count = 0

with open("input") as f:
    for line in f: 
        if is_valid(line.strip()):
            count += 1

print(count)
