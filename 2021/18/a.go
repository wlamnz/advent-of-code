package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

type Direction int

const (
	Left Direction = iota
	Right
)

type SFNumber struct {
	left  SFNumberOrRegularNumber
	right SFNumberOrRegularNumber
}

type SFNumberOrRegularNumber struct {
	rn  *int
	sfn *SFNumber
}

func (sfn SFNumber) String() string {
	return "[" + sfn.left.String() + "," + sfn.right.String() + "]"
}

func (sfnOrRn SFNumberOrRegularNumber) String() string {
	if sfnOrRn.rn != nil {
		return strconv.FormatInt(int64(*sfnOrRn.rn), 10)
	}

	return (*sfnOrRn.sfn).String()
}

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
		return
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	scanner.Scan()
	sum := parse(scanner.Text())

	for scanner.Scan() {
		sum = add(sum, parse(scanner.Text()))
	}

	fmt.Println(magnitude(sum))
}

func parse(line string) SFNumber {
	var stack []rune
	pairMidPoint := -1

	withoutOuterSqBrackets := line[1 : len(line)-1]

	for i, c := range withoutOuterSqBrackets {
		if c == '[' {
			stack = append(stack, c)
		} else if c == ']' {
			n := len(stack) - 1
			stack = stack[:n]
		} else if c == ',' && len(stack) == 0 {
			pairMidPoint = i
			break
		}
	}

	left := withoutOuterSqBrackets[0:pairMidPoint]
	right := withoutOuterSqBrackets[pairMidPoint+1:]

	leftSfnOrRn := SFNumberOrRegularNumber{}
	rightSfnOrRn := SFNumberOrRegularNumber{}

	if len(left) == 1 {
		v, _ := strconv.Atoi(left)
		leftSfnOrRn.rn = &v
	} else {
		v := parse(left)
		leftSfnOrRn.sfn = &v
	}

	if len(right) == 1 {
		v, _ := strconv.Atoi(right)
		rightSfnOrRn.rn = &v
	} else {
		v := parse(right)
		rightSfnOrRn.sfn = &v
	}

	return SFNumber{leftSfnOrRn, rightSfnOrRn}
}

func depth(sfn SFNumber, level int) int {
	if sfn.left.sfn == nil && sfn.right.sfn == nil {
		return level
	}

	v := 0

	if sfn.left.sfn != nil {
		v = depth(*sfn.left.sfn, level+1)
	}

	if sfn.right.sfn != nil {
		v = max(v, depth(*sfn.right.sfn, level+1))
	}

	return v
}

func (sfn *SFNumber) requiresExplode() bool {
	return depth(*sfn, 0) >= 4
}

func (sfn *SFNumber) doExplode(level int) (int, Direction, bool) {
	exploded := false

	if sfn.left.sfn != nil {
		if level == 3 {
			sfnv := *sfn.left.sfn

			if sfn.right.rn != nil {
				rightRn := *sfn.right.rn
				rightRn += *sfnv.right.rn
				sfn.right.rn = &rightRn
			} else {
				p := sfn.right.sfn
				for p.left.sfn != nil {
					p = p.left.sfn
				}

				rightRn := *p.left.rn
				rightRn += *sfnv.right.rn
				p.left.rn = &rightRn
			}

			v := 0
			sfn.left.rn = &v
			sfn.left.sfn = nil

			return *sfnv.left.rn, Left, true
		} else {
			v, dir, tmp := sfn.left.sfn.doExplode(level + 1)
			exploded = tmp

			if v != 0 {
				if dir == Right {
					if sfn.right.rn != nil {
						rightRn := *sfn.right.rn
						rightRn += v
						sfn.right.rn = &rightRn
					} else {
						p := sfn.right.sfn
						for p.left.sfn != nil {
							p = p.left.sfn
						}

						rightRn := *p.left.rn
						rightRn += v
						p.left.rn = &rightRn
					}
				} else {
					return v, dir, exploded
				}
			}
		}
	}

	if exploded {
		return 0, Left, true
	}

	if sfn.right.sfn != nil {
		if level == 3 {
			sfnv := *sfn.right.sfn

			if sfn.left.rn != nil {
				leftRn := *sfn.left.rn
				leftRn += *sfnv.left.rn
				sfn.left.rn = &leftRn
			} else {
				p := sfn.left.sfn
				for p.right.sfn != nil {
					p = p.right.sfn
				}

				leftRn := *p.right.rn
				leftRn += *sfnv.left.rn
				p.right.rn = &leftRn
			}

			v := 0
			sfn.right.rn = &v
			sfn.right.sfn = nil

			return *sfnv.right.rn, Right, true
		} else {
			v, dir, tmp := sfn.right.sfn.doExplode(level + 1)
			exploded = tmp

			if v != 0 {
				if dir == Left {
					if sfn.left.rn != nil {
						leftRn := *sfn.left.rn
						leftRn += v
						sfn.left.rn = &leftRn
					} else {
						p := sfn.left.sfn
						for p.right.sfn != nil {
							p = p.right.sfn
						}

						leftRn := *p.right.rn
						leftRn += v
						p.right.rn = &leftRn
					}
				} else {
					return v, dir, exploded
				}
			}
		}
	}

	if exploded {
		return 0, Right, true
	}

	return 0, Left, exploded
}

func (sfn *SFNumber) explode() {
	sfn.doExplode(0)
}

func (sfn *SFNumber) split() bool {
	splited := false
	if sfn.left.sfn != nil {
		splited = sfn.left.sfn.split()
	} else {
		rn := *sfn.left.rn

		if rn >= 10 {
			half := rn / 2

			if rn&1 == 1 {
				halfPlusOne := half + 1
				sfn.left.sfn = &SFNumber{SFNumberOrRegularNumber{rn: &half}, SFNumberOrRegularNumber{rn: &halfPlusOne}}
			} else {
				sfn.left.sfn = &SFNumber{SFNumberOrRegularNumber{rn: &half}, SFNumberOrRegularNumber{rn: &half}}
			}

			sfn.left.rn = nil
			return true
		}
	}

	if splited {
		return true
	}

	if sfn.right.sfn != nil {
		splited = sfn.right.sfn.split()
	} else {
		rn := *sfn.right.rn

		if rn >= 10 {
			half := rn / 2

			if rn&1 == 1 {
				halfPlusOne := half + 1
				sfn.right.sfn = &SFNumber{SFNumberOrRegularNumber{rn: &half}, SFNumberOrRegularNumber{rn: &halfPlusOne}}
			} else {
				sfn.right.sfn = &SFNumber{SFNumberOrRegularNumber{rn: &half}, SFNumberOrRegularNumber{rn: &half}}
			}

			sfn.right.rn = nil
			return true
		}
	}

	return splited
}

func (sfn *SFNumber) requiresSplit() bool {
	res := false
	if sfn.left.sfn != nil {
		res = sfn.left.sfn.requiresSplit()
	} else {
		rn := *sfn.left.rn

		if rn >= 10 {
			return true
		}
	}

	if res {
		return true
	}

	if sfn.right.sfn != nil {
		res = sfn.right.sfn.requiresSplit()
	} else {
		rn := *sfn.right.rn

		if rn >= 10 {
			return true
		}
	}

	return res
}

func (sfn *SFNumber) reduce() {
	for {
		if sfn.requiresExplode() {
			sfn.explode()
			continue
		}

		if sfn.requiresSplit() {
			sfn.split()
			continue
		}

		break
	}
}

func add(sfn1, sfn2 SFNumber) SFNumber {
	newSfn := SFNumber{SFNumberOrRegularNumber{sfn: &sfn1}, SFNumberOrRegularNumber{sfn: &sfn2}}
	newSfn.reduce()
	return newSfn
}

func magnitude(sfn SFNumber) int {
	v := 0

	if sfn.left.sfn != nil {
		left := *sfn.left.sfn
		v += magnitude(left) * 3
	} else {
		left := *sfn.left.rn
		v += left * 3
	}

	if sfn.right.sfn != nil {
		right := *sfn.right.sfn
		v += magnitude(right) * 2
	} else {
		right := *sfn.right.rn
		v += right * 2
	}

	return v
}

func max(a, b int) int {
	if a > b {
		return a
	}

	return b
}
