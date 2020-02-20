package com.accessor.eldorado.game

/**
 * Данные слотов
 * Матрица 3х3
 * Инициализация данных рандомно от 0..2
 */
class SlotData {

    private var slot = Array(3) { IntArray(3) }

    operator fun set(x: Int, y: Int, v: Int) {
        slot[x][y] = v
    }

    operator fun get(x: Int, y: Int): Int {
        return slot[x][y]
    }


    fun init() {
        for (y in 0..2) {
            val range = (0..2)
            for (x in range) {
                val v = range.random()
                this[x, y] = v
            }
        }
    }
}