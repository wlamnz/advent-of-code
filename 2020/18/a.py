#!/usr/bin/python3

total = 0
with open("input") as f:
    for line in f:
        s = []
        q = []

        tokens = line.strip().replace("(", "( ").replace(")", " )").split(" ")

        for token in tokens:
            if token == "+" or token == "*":
                if s and s[-1] != "(":
                    q.append(s.pop())
                s.append(token)
            elif token == "(":
                s.append(token)
            elif token == ")":
                while True:
                    op = s.pop()
                    if op == "(":
                        break
                    q.append(op)
            else:
                q.append(int(token))

        while s:
            q.append(s.pop())

        while q:
            token = q.pop(0)

            if token == "+":
                operand1 = s.pop()
                operand2 = s.pop()
                
                s.append(operand1 + operand2)
            elif token == "*":
                operand1 = s.pop()
                operand2 = s.pop()
                
                s.append(operand1 * operand2)
            else:
                s.append(token)

        total += s.pop()

print(total)
