#!/usr/bin/python3

cups = [int(x) for x in list("871369452")]
S = len(cups)
p = 0
last_current_cup = -1

for i in range(100):
    if last_current_cup != -1:
        p = cups.index(last_current_cup)
        p += 1
        p %= S

    current = cups[p]
    pick_up = cups[p + 1 : p + 4]
    if len(pick_up) < 3:
        pick_up += cups[:3 - len(pick_up)]
    destination = S if current == 1 else current - 1

    while destination in pick_up:
        destination = S if destination == 1 else destination - 1

    remaining = [n for n in cups if n not in pick_up]
    idx = remaining.index(destination)
    cups = remaining[:idx + 1] + pick_up + remaining[idx + 1:] 

    last_current_cup = current

one_index = cups.index(1)
labels_after_1 = []
for i in range(1, S):
    labels_after_1.append(str(cups[(one_index + i) % S]))

print("".join(labels_after_1))
