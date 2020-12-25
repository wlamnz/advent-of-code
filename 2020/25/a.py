#!/usr/bin/python3

def get_loop_size(subject_number, pub_key):
    v = 1
    loop_size = 1
    while True:
        v *= subject_number
        v %= 20201227

        if v == pub_key:
            break

        loop_size += 1

    return loop_size

def get_encryption_key(subject_number, loop_size):
    v = 1
    for i in range(loop_size):
        v *= subject_number
        v %= 20201227

    return v 

card_pub_key = 5764801
door_pub_key = 17807724
with open("input") as f:
    card_pub_key = int(f.readline())
    door_pub_key = int(f.readline())

l1 = get_loop_size(7, card_pub_key)
l2 = get_loop_size(7, door_pub_key)

k1 = get_encryption_key(door_pub_key, l1)
k2 = get_encryption_key(card_pub_key, l2)

assert k1 == k2
print(k1)
