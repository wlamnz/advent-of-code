#!/usr/bin/python3

rules = {}

def solve(i, s, rule_key):
    if i == len(s):
        return []

    paths = rules[rule_key]
    indexes = []

    for path in paths:
        iis = [i]
        for t in path:
            new_iis = []

            if t.startswith('"'): 
                for ii in iis:
                    if ii == len(s):
                        continue
                    elif s[ii] == t[1:-1]:
                        new_iis.append(ii + 1)
            else:
                for ii in iis:
                    for n_ii in solve(ii, s, t):
                        new_iis.append(n_ii)

            iis = new_iis

        for ii in iis:
            indexes.append(ii)
    
    return indexes
            
matches = 0

with open("input") as f:
    for line in f:
        line = line.strip()

        if line:
            lhs, rhs = line.split(": ")
            disj = [p.split() for p in rhs.split(" | ")]
            rules[lhs] = disj
        else:
            break

    rules["8"] = [["42"], ["42", "8"]]
    rules["11"] = [["42", "31"], ["42", "11", "31"]]

    for line in f:
        line = line.strip()
        if any(i == len(line) for i in solve(0, line, "0")):
            matches += 1

print(matches)
