#!/usr/bin/python3

memory = {}
mask = None

with open("input") as f:
    for line in f:
        lhs, rhs = line.strip().split(" = ")
        if lhs == "mask":
            mask = rhs
        else:
            address = int(lhs[4:-1])
            v = int(rhs)

            res = 0
            for i in range(len(mask)):
                ri = -1 - i
                c = mask[ri]

                if c == 'X':
                    res += (1 << i) & v
                elif c == '1':
                    res += (1 << i)

            memory[address] = res

total = 0
for v in memory.values():
    total += v

print(total)
