#!/usr/bin/python3

instructions = []
with open("input") as f:
    for line in f:
        line = line.strip()
        opcode, operand = line.split()
        instructions.append((opcode, int(operand)))

visited = set()
idx = 0
accumulator = 0
while True:
    if idx in visited:
        break

    visited.add(idx)
    opcode, operand = instructions[idx]
    if opcode == "acc":
        accumulator += operand
    elif opcode == "jmp":
        idx += operand
        continue
    
    idx += 1

print(accumulator)
