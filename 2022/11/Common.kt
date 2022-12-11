package `11`

import java.util.function.LongFunction
import java.util.function.LongPredicate

class Monkey(val id: Int, val items: ArrayDeque<Long>, val operation: LongFunction<Long>,
                      val test: LongPredicate, val idToThrowIfTrue: Int, val idToThrowIfFalse: Int)