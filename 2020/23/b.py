#!/usr/bin/python3

class Node:

    def __init__(self, v):
        self.next_node = None
        self.v = v

    def get_value(self):
        return self.v

    def set_next(self, next_node):
        self.next_node = next_node

    def get_next(self):
        return self.next_node

cups = [int(x) for x in list("871369452")]
cups += [n for n in range(10, 1000001)]
S = len(cups)

nodes = [None for _ in range(len(cups))]

for c in cups:
    nodes[c - 1] = Node(c)

for i in range(len(cups)):
    nodes[cups[i] - 1].set_next(nodes[cups[(i + 1) % len(cups)] - 1])

current_node = nodes[cups[0] - 1]
for i in range(10000000):
    pick_up = []

    it = current_node
    for j in range(3):
        it = it.get_next()
        pick_up.append(it.get_value())

    current_value = current_node.get_value()
    destination = len(cups) if current_value == 1 else current_value - 1

    while destination in pick_up:
        destination = len(cups) if destination == 1 else destination - 1

    destination_node = nodes[destination - 1] 
    old_destination_next = destination_node.get_next()
    destination_node.set_next(current_node.get_next())
    old_it_next = it.get_next()
    it.set_next(old_destination_next)
    current_node.set_next(old_it_next)
    current_node = current_node.get_next()

print(nodes[0].get_next().get_value() * nodes[0].get_next().get_next().get_value())
