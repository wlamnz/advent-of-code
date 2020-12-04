#!/usr/bin/python3

def is_birth_year_valid(birth_year):
    return birth_year and int(birth_year) >= 1920 and int(birth_year) <= 2002

def is_issue_year_valid(issue_year):
    return int(issue_year) >= 2010 and int(issue_year) <= 2020 

def is_expiration_year_valid(expiration_year):
    return int(expiration_year) >= 2020 and int(expiration_year) <= 2030 

def is_height_valid(height):
    try:
        height_value = int(height[:-2])
       
        if height.endswith("cm"):
           return height_value >= 150 and height_value <= 193 
        elif height.endswith("in"):
            return height_value >= 59 and height_value <= 76
    except:
        return False

def is_hair_colour_valid(hair_colour):
    return len(hair_colour) == 7 and hair_colour[0] == "#" and all([(c >= "0" and c <= "9") or (c >= "a" and c <= "f") for c in hair_colour[1:]])

def is_eye_colour_valid(eye_colour):
    return eye_colour in ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]


def is_passport_id_valid(pid):
    return len(pid) == 9 and all([c >= "0" and c <= "9" for c in pid])

def is_valid(passport):
    if all([f in passport for f in ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]]):
        return (is_birth_year_valid(passport["byr"]) 
            and is_issue_year_valid(passport["iyr"])
            and is_expiration_year_valid(passport["eyr"])
            and is_height_valid(passport["hgt"])
            and is_hair_colour_valid(passport["hcl"])
            and is_eye_colour_valid(passport["ecl"])
            and is_passport_id_valid(passport["pid"]))

    return False

total = 0
passport = {}

with open("input") as f:
    for line in f:
        line = line.strip()
        if line == "":
            if is_valid(passport):
                total += 1
            passport = {}
        else:
            for field in line.split():
                k, v = field.split(":")
                passport[k] = v

# Edge case to deal with last passport
if len(passport) > 0 and is_valid(passport):
    total += 1

print(total)
