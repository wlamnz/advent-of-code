#!/usr/bin/python3
import re

rules = {}

with open("input") as f:
    for line in f:
        line = line.strip()[:-1]
        parts = re.split(' contain |, ', line)
        bag_key = parts[0][:-1]
        contents = []
        
        for i in range(1, len(parts)):
            content = parts[i] 
            if content != 'no other bags':
                content_parts = content.split() 
                quantity = int(content_parts[0])
                bag = ' '.join(content_parts[1:])
                if bag.endswith("s"):
                    bag = bag[:-1]
                contents.append((quantity, bag)) 

        rules[bag_key] = contents

q = ["shiny gold bag"]
seen = set()

while q:
    bag = q.pop(0)
    if bag in seen:
        continue

    seen.add(bag)

    for k, contents in rules.items():
        if any([other_bag == bag for quantity, other_bag in contents]):
            q.append(k)

print(len(seen) - 1) # minus the shiny gold bag
