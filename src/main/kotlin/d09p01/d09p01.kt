package d09p01

import java.io.File

sealed class RadixCipher {
    fun add(i: Long): RadixCipher = add(i, 0)
    abstract fun add(i: Long, depth: Int): RadixCipher
    fun remove(i: Long): RadixCipher = remove(i, 0)
    abstract fun remove(i: Long, depth: Int): RadixCipher
    fun contains(i: Long): Boolean {
        for(j in 0..(i / 2))
            if(contains(j, 0) and contains(i - j, 0))
                return true

        return false
    }
    abstract fun contains(i: Long, depth: Int): Boolean
}

class RadixBucket private constructor(private val bucket: Array<RadixCipher>) : RadixCipher() {
    private fun getRadix(num: Long, depth: Int) = ((num ushr (depth * 4)) and 0xf).toInt() //bitwise operations are a weak point of kotlin

    constructor(i: Long, j: Long, depth: Int) : this(Array(16) { RadixNone }) {
        val iRadix = getRadix(i, depth)
        val jRadix = getRadix(j, depth)
        if(iRadix != jRadix) {
            bucket[iRadix] = RadixSome(i)
            bucket[jRadix] = RadixSome(j)
        }
        else
            bucket[iRadix] = RadixBucket(i, j, depth + 1)
    }

    override fun add(i: Long, depth: Int): RadixCipher {
        bucket[getRadix(i, depth)] = bucket[getRadix(i, depth)].add(i, depth + 1)
        return this
    }
    override fun remove(i: Long, depth: Int): RadixCipher {
        bucket[getRadix(i, depth)] = bucket[getRadix(i, depth)].remove(i, depth + 1)
        return if(bucket.all { it == RadixNone }) RadixNone
        else this
    }
    override fun contains(i: Long, depth: Int) = bucket[getRadix(i, depth)].contains(i, depth + 1)
}

class RadixSome(val num: Long) : RadixCipher() {
    override fun add(i: Long, depth: Int) =
        if (i == num) this
        else RadixBucket(num, i, depth)

    override fun remove(i: Long, depth: Int) =
        if (i == num) RadixNone
        else this

    override fun contains(i: Long, depth: Int) = i == num
}

object RadixNone : RadixCipher() {
    override fun add(i: Long, depth: Int) = RadixSome(i)
    override fun remove(i: Long, depth: Int) = RadixNone
    override fun contains(i: Long, depth: Int) = false
}

fun main(args: Array<String>) {
    val numbers =
        File(args[0])
            .readText().split("\n")
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
            .toMutableList()

    val buffer = 25

    val removeQueue = MutableList(0) { 0L }
    var radixCipher: RadixCipher = RadixNone

    for(i in 0 until buffer) {
        val num = numbers.removeFirst()
        removeQueue.add(num)
        radixCipher = radixCipher.add(num)
    }

    while(numbers.any()) {
        val num = numbers.removeFirst()
        if(!radixCipher.contains(num)) {
            println(num)
            break
        }

        radixCipher = radixCipher.remove(removeQueue.removeFirst()).add(num)
        removeQueue.add(num)
    }
    //258585477
}