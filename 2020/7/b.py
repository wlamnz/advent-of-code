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

def solve(bag):
    total = 1
    for quantity, other_bag in rules[bag]:
       total += quantity * solve(other_bag) 

    return total

total = solve("shiny gold bag")

print(total - 1) # minus the shiny gold bag
