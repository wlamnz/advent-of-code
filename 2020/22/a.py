#!/usr/bin/python3

player1 = []
player2 = []

with open("input") as f:
    is_player1 = True
    for line in f:
        line = line.strip()

        if line.startswith("Player"):
            continue
        elif not line:
            is_player1 = False
            continue

        card = int(line)

        if is_player1:
            player1.append(card)
        else:
            player2.append(card)

while player1 and player2:
    c1 = player1.pop(0)
    c2 = player2.pop(0)

    assert c1 != c2

    if c1 > c2:
        player1.append(c1)
        player1.append(c2)
    elif c2 > c1:
        player2.append(c2)
        player2.append(c1)

def compute_score(deck):
    ans = 0
    for i, v in enumerate(deck):
        ans += v * (len(deck) - i)
    return ans

print(compute_score(player1) + compute_score(player2))
