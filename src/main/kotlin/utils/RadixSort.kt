package utils

sealed class RadixSort {
    fun add(i: Int) = add(i, 0)
    abstract fun add(i: Int, depth: Int): RadixSort
    abstract fun toList(l: MutableList<Int>)
    fun toList(): List<Int> {
        val l = mutableListOf<Int>()
        toList(l)
        return l.toList()
    }
}

class RadixBucket private constructor (private val buckets: Array<RadixSort>): RadixSort() {
    private fun getRadix(num: Int, depth: Int) = ((num ushr ((15 - depth) * 4)) and 0xf)

    constructor(i: Int, j: Int, depth: Int) : this(Array(16) { RadixNone }) {
        val iRadix = getRadix(i, depth)
        val jRadix = getRadix(j, depth)
        if(iRadix != jRadix) {
            buckets[iRadix] = RadixSome(i)
            buckets[jRadix] = RadixSome(j)
        }
        else
            buckets[iRadix] = RadixBucket(i, j, depth + 1)
    }

    override fun add(i: Int, depth: Int): RadixSort {
        buckets[getRadix(i, depth)] = buckets[getRadix(i, depth)].add(i, depth + 1)
        return this
    }

    override fun toList(l: MutableList<Int>) {
        buckets.forEach { it.toList(l) }
    }
}

class RadixSome(val value: Int): RadixSort() {
    override fun add(i: Int, depth: Int) =
        if(i == value) this
        else RadixBucket(value, i, depth)

    override fun toList(l: MutableList<Int>) {
        l.add(value)
    }
}

object RadixNone: RadixSort() {
    override fun add(i: Int, depth: Int) = RadixSome(i)
    override fun toList(l: MutableList<Int>) {}
}
