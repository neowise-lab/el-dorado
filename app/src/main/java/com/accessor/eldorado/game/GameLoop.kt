package com.accessor.eldorado.game

/**
 * Класс игрового потока
 * Думаю тут все понятно
 */
class GameLoop(private val view: DrawView) : Thread() {

    private val FPS = 10
    var running = false

    override fun run() {

        val ticksPS = 1000 / FPS
        var startTime: Long
        var sleepTime: Long

        while (running) {
            startTime = System.currentTimeMillis()
            val canvas = view.holder.lockCanvas()
            view.draw(canvas)
            view.holder.unlockCanvasAndPost(canvas)

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime)

            if (sleepTime > 0)
                sleep(sleepTime)
            else
                sleep(10)
        }
    }
}