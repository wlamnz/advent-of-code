#!/usr/bin/python

with open("input") as f:
    nums = [int(line.strip()) for line in f]

nums = sorted(nums)
nums.insert(0, 0)

dp = [0] * len(nums)
dp[0] = 1

for i in range(1, len(nums)):
    x = nums[i]

    for j in range(i):
        y = nums[j]
        if y >= x - 3:
            dp[i] += dp[j]

print(dp[-1])
