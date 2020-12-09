#!/usr/bin/python3

with open("input") as f:
    nums = [int(line.strip()) for line in f]

preamble_length = 25 
v = None
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
        v = n
        break

cumu_sum = []
cumu_sum.append(nums[0])

for i in range(1, len(nums)):
    n = nums[i]
    cumu_sum.append(cumu_sum[i - 1] + n)

for i in range(len(nums)):
    found = False
    for j in range(i + 1, len(nums)):
        if cumu_sum[j] - (i == 0 if 0 else cumu_sum[i - 1]) == v:
            sub = nums[i : j + 1]
            print(min(sub) + max(sub))
            found = True
            break
    if found:
        break
