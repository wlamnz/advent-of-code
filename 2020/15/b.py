#!/usr/bin/python3

with open("input") as f:
    nums = [int(n) for n in f.readline().split(",")]

mem = {}
for i, n in enumerate(nums, 1):
    mem[n] = i

i = len(nums) + 1
last = nums[-1]
while True:
    spoken = 0
    if last in mem:
        spoken = (i - 1) - mem[last]

    mem[last] = (i - 1)
    last = spoken

    if i == 30000000:
        print(last)
        break

    i += 1
