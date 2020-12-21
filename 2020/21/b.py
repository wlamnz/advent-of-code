#!/usr/bin/python3

ingredients_by_allergens = {}
all_allergens = set()
ingredients_count = {}
with open("input") as f:
    for line in f:
        line = line.strip()[:-1]
        ingredient_list, allergens = line.split(" (contains ")
        
        ingredients = ingredient_list.split()
        new_ingredient_set = set()
        for ingredient in ingredients:
            if ingredient not in ingredients_count:
                ingredients_count[ingredient] = 0

            ingredients_count[ingredient] += 1
            new_ingredient_set.add(ingredient)

        for allergen in allergens.split(", "):
            all_allergens.add(allergen)
            if allergen in ingredients_by_allergens:
                to_be_removed = []
                for ingredient in ingredients_by_allergens[allergen]:
                    if ingredient not in new_ingredient_set:
                        to_be_removed.append(ingredient)

                for ingredient in to_be_removed:
                    ingredients_by_allergens[allergen].remove(ingredient)
            else:
                ingredients_by_allergens[allergen] = set(ingredients)

while True:
    to_be_removed = []
    for k1, v1 in ingredients_by_allergens.items():
        if len(v1) > 1:
            for a in v1:
                for k2, v2 in ingredients_by_allergens.items():
                    if len(v2) == 1 and k1 != k2 and a in v2:
                        to_be_removed.append((k1, a))
                        break
    if len(to_be_removed) == 0:
        break

    for t in to_be_removed:
        ingredients_by_allergens[t[0]].remove(t[1])

canonical_list = []
for allergen in sorted(all_allergens):
    canonical_list += ingredients_by_allergens[allergen]

print(",".join(canonical_list))
