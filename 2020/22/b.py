#!/usr/bin/python3
from functools import reduce

player1 = []
player2 = []
prime = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 
        73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 
        157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229]

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

def compute_hash(deck):
    h = 0
    for i, v in enumerate(deck):
        h += prime[i] * v
    return h

def play(deck1, deck2):
    seen = set()

    while deck1 and deck2:
        h = compute_hash(deck1) * compute_hash(deck2)

        if h in seen:
            return True
        seen.add(h)

        c1 = deck1.pop(0)
        c2 = deck2.pop(0)

        assert c1 != c2

        if len(deck1) >= c1 and len(deck2) >= c2:
            does_player_1_win = play(deck1[:c1], deck2[:c2])
            if does_player_1_win:
                deck1.append(c1)
                deck1.append(c2)
            else:
                deck2.append(c2)
                deck2.append(c1)
        elif c1 > c2:
            deck1.append(c1)
            deck1.append(c2)
        elif c2 > c1:
            deck2.append(c2)
            deck2.append(c1)

    return len(deck1) > len(deck2)

def compute_score(deck):
    ans = 0
    for i, v in enumerate(deck):
        ans += v * (len(deck) - i)
    return ans

play(player1, player2)
print(compute_score(player1) + compute_score(player2))
