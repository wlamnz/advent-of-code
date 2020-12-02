#!/usr/bin/python3

nums = []
dict = {}

with open("input") as f:
    for i, line in enumerate(f):
        num = int(line.strip())
        nums.append(num)
        if num not in dict:
            dict[num] = set()

        dict[num].add(i)

for i in range(len(nums)):
    n1 = nums[i]

    for j in range(i + 1, len(nums)):
        n2 = nums[j]

        n3 = 2020 - n1 - n2
        # Note: Ensure the index of n3 isn't i or j for uniqueness
        if n3 in dict and i not in dict[n3] and j not in dict[n3]:
            print(n1 * n2 * n3)
            break
    else:
        continue
    break
