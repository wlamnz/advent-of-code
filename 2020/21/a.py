#!/usr/bin/python3

ingredients_by_allergens = {}
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
            if allergen in ingredients_by_allergens:
                to_be_removed = []
                for ingredient in ingredients_by_allergens[allergen]:
                    if ingredient not in new_ingredient_set:
                        to_be_removed.append(ingredient)

                for ingredient in to_be_removed:
                    ingredients_by_allergens[allergen].remove(ingredient)
            else:
                ingredients_by_allergens[allergen] = set(ingredients)

total = 0
for ingredient, count in ingredients_count.items():
    contains_allergen = False
    for ingredients_with_allergen in ingredients_by_allergens.values():
        if ingredient in ingredients_with_allergen:
            contains_allergen = True
            break

    if not contains_allergen:
        total += count

print(total)
