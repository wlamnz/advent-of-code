#!/usr/bin/python3

memory = {}
mask = None

def get_addresses(p_address, indices):
    addresses = []

    for i in range(1 << len(indices)):
        new_address = p_address

        for j, idx in enumerate(indices):
            bit = (i >> j) & 1
            new_address += bit << idx

        addresses.append(new_address)

    return addresses

with open("input") as f:
    for line in f:
        lhs, rhs = line.strip().split(" = ")
        if lhs == "mask":
            mask = rhs
        else:
            address = int(lhs[4:-1])
            v = int(rhs)

            res = 0
            indices = []
            for i in range(len(mask)):
                ri = -1 - i
                c = mask[ri]

                if c == 'X':
                    indices.append(i)
                elif c == '1':
                    res += (1 << i)
                elif c == '0':
                    res += (1 << i) & address

            addresses = get_addresses(res, indices)

            for w_address in addresses:
                memory[w_address] = v 

total = 0
for v in memory.values():
    total += v

print(total)
