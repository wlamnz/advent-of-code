#!/usr/bin/python3

with open("input") as f:
    nums = [int(line.strip()) for line in f] 

lookup_set = set()
lookup_set.add(nums[0])

for i in range(1, len(nums)):
    n = nums[i]
    if 2020 - n in lookup_set:
        print(n * (2020 - n))
        break

    lookup_set.add(n)
