#!/usr/bin/python3

instructions = []
with open("input") as f:
    for line in f:
        line = line.strip()
        opcode, operand = line.split()
        instructions.append((opcode, int(operand)))

def solve(instructions):
    visited = set()
    idx = 0
    accumulator = 0
    while True:
        if idx in visited:
            return None
        if idx == len(instructions):
            break

        visited.add(idx)
        opcode, operand = instructions[idx]
        if opcode == "acc":
            accumulator += operand
        elif opcode == "jmp":
            idx += operand
            continue
        
        idx += 1

    return accumulator

for i in range(len(instructions)):
    opcode, operand = instructions[i]
    if opcode == "nop":
        instructions[i] = ("jmp", operand)
        v = solve(instructions)
        if v != None:
            print(v)
            break
        else:
            instructions[i] = ("nop", operand)
    elif opcode == "jmp":
        instructions[i] = ("nop", operand) 
        v = solve(instructions)
        if v != None:
            print(v)
            break
        else:
            instructions[i] = ("jmp", operand)
