#!/usr/bin/python

with open("input") as f:
    nums = [int(line.strip()) for line in f]

nums = sorted(nums)

one_jolt = 0
three_jolt = 0

last = 0

for n in nums:
    if n - last == 1:
        one_jolt += 1
    elif n - last == 3:
        three_jolt += 1

    last = n

three_jolt += 1

print(one_jolt * three_jolt)
