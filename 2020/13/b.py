#!/usr/bin/python3
from functools import reduce

with open("input") as f:
    earliest = int(f.readline().strip())
    ids = f.readline().strip().split(',')

bids = []
offsets = []

for i, bid in enumerate(ids): 
    if ids[i] != "x":
        bids.append(int(bid))
        offsets.append(i)

def egcd(a, b):  
    if a == 0:   
        return (b, 0, 1)
             
    gcd, x1, y1 = egcd(b % a, a)  
     
    x = y1 - (b//a) * x1  
    y = x1  
     
    return (gcd, x, y)

M = reduce(lambda a, b: a * b, bids)
t = 0

for bid, offset in zip(bids, offsets):
    a = bid - offset
    m = M // bid
    m_inverse = egcd(m, bid)[1]
    t += (a % M * m % M * m_inverse % M)

t %= M
print(t)
