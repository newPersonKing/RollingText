package com.yy.mobile.rollingtextview.util

import android.util.Log

/**
 * Created by 张宇 on 2018/2/27.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 *
 * 可以把原来的[list]改造成循环列表
 *
 * 比如原来的列表为 a = <1,2,3>,如果有 b = CircularList(a, 6), 那么 b 列表遍历结果为 <1,2,3,1,2,3>,
 * 如果有 c = CircularList(a, 6, 1), 那么 c 列表的遍历结果为 <2,3,1,2,3,1>
 *
 * @param list 原来的列表
 * @param size 新列表的总大小
 * @param startIndex 新列表的第一个元素从原列表的startIndex索引值开始
 */
class CircularList<T> @JvmOverloads constructor(
        private val list: List<T>,
        override val size: Int,
        private val startIndex: Int = 0
) : List<T> by list {

    private val rawSize = list.size

    override fun get(index: Int): T {
        try {
            val rawIndex = (index + startIndex) % rawSize
            return list[rawIndex]
        } catch (e: ArithmeticException) {
            Log.i("zycheck", "1")
            throw e
        }
    }

    override fun isEmpty() = size <= 0

    override fun indexOf(element: T) = indexOfFirst { it == element }

    override fun lastIndexOf(element: T) = indexOfLast { it == element }

    override fun subList(fromIndex: Int, toIndex: Int): List<T> =
            CircularList(list, toIndex - fromIndex, size + fromIndex)

    override fun iterator(): Iterator<T> = listIterator()

    override fun listIterator(): ListIterator<T> = listIterator(0)

    override fun listIterator(index: Int): ListIterator<T> = CircularIterator(index)

    private inner class CircularIterator(private var index: Int = 0) : ListIterator<T> {

        init {
            if (index < 0 || index > size) {
                throw ArrayIndexOutOfBoundsException("index should be in range [0,$size] but now is $index")
            }
        }

        override fun hasNext(): Boolean = index < size

        override fun next(): T {
            if (!hasNext()) throw NoSuchElementException()
            return get(index++)
        }

        override fun hasPrevious(): Boolean = index > 0

        override fun nextIndex(): Int = index

        override fun previous(): T {
            if (!hasPrevious()) throw NoSuchElementException()
            return get(--index)
        }

        override fun previousIndex(): Int = index - 1
    }

    override fun toString(): String {
        val sb = StringBuilder("[")
        iterator().forEach {
            sb.append("$it ")
        }
        return sb.append("]").toString()
    }
}