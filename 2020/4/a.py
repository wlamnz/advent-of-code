#!/usr/bin/python3

def is_valid(passport):
    return all([f in passport for f in ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]])

total = 0
passport = {}

with open("input") as f:
    for line in f:
        line = line.strip()
        if line == "":
            if is_valid(passport):
                total += 1
            passport = {}
        else:
            for field in line.split():
                k, v = field.split(":")
                passport[k] = v

# Edge case to deal with last passport
if len(passport) > 0 and is_valid(passport):
    total += 1

print(total)
