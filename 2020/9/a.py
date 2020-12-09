#!/usr/bin/python3

with open("input") as f:
    nums = [int(line.strip()) for line in f]

preamble_length = 25 
for i in range(preamble_length, len(nums)): 
    n = nums[i]
    found = False

    for j in range(i - preamble_length, i):
        for k in range(j + 1, i):
            if nums[j] + nums[k] == n:
                found = True
                break
        if found:
            break

    if not found:
        print(n)
        break
