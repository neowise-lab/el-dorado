package com.accessor.eldorado.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Handler
import android.view.SurfaceHolder
import com.accessor.eldorado.R

/**
 * Класс прорисовки игры
 */
class DrawView(private val context: Context, val holder: SurfaceHolder) : SurfaceHolder.Callback {

    /**игровой поток*/
    private lateinit var gameLoop: GameLoop

    /**спрайты*/
    private lateinit var background: Sprite
    private lateinit var slotMachine: Sprite
    private lateinit var gold: Sprite
    private lateinit var slotBox: Sprite

    /**gameItems*/
    private val items = mutableListOf<Sprite>()

    /** Флаг первой инициализации
     * т.е. если заход первый то происходит инциализация, иначе пропускается
     * */
    private var firstInitialization = true

    /** Флаг первой инициализации
     * т.е. если заход первый то происходит инциализация, иначе пропускается
     * */
    private var drawOnlyBackground = true
    /**
     * Текущий статус игры
     * Если ничего не нажато WAIT
     * Нажимается кнопка Spin то SPIN
     */
    private var state = State.WAIT

    /** Хранит данные слотов
     * Массив 3х3
     * рандомная ининиализация
     */
    private val slotData = SlotData()

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        /** инициализируем данные слотов*/
        slotData.init()
        /** запускаем игровой цикл*/
        gameLoop = GameLoop(this)
        gameLoop.running = true
        gameLoop.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        /** завершаем цикл*/
        stop()
    }

    fun stop() {
        var retry = true
        gameLoop.running = false

        /** ждем пока все завершится*/
        while (retry) {
            gameLoop.join()
            retry = false
        }
    }

    fun draw(canvas: Canvas) {
        /***
         * Так как загрузка ресурсов занимает много времени
         * будем в первом заходе рисум фон и возвращаемся
         * и не будет черного экрана surfaceView
         */
        if (drawOnlyBackground) {
            val backgroundBitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.hallway1)
            background = Sprite(
                Bitmap.createScaledBitmap(
                    backgroundBitmap,
                    canvas.width,
                    canvas.height,
                    true
                ), 0, 0
            )
            background.draw(canvas)

            drawOnlyBackground = false
            return
        }

        /***
         * Если инциализация ресурсов не пройдена
         * делаем инциализацию
         */
        if (firstInitialization) initResources(canvas)
        firstInitialization = false

        background.draw(canvas)

        /**рисуем фон*/
        /**золото*/
        gold.draw(canvas)
        /**Бокс для слотов*/
        slotBox.draw(canvas)

        /**Если на данный момент идет игра (т.е. была нажата кнопка Spin)
         * переиницализируем данные*/
        if (state == State.SPIN) {
            slotData.init()
        }

        /**определяем высоту и ширину колонки слота */
        val width = slotBox.width / 3
        val height = (items[0].height + 10) * 3

        /**рисуем элементы слота исходя из данных SlotData */
        for (i in 0..2) {
            for (j in 0..2) {
                val sprite = items[slotData[i, j]]
                val columnX = slotBox.x + (width * j) + (width - sprite.width) / 2
                val columnY = slotBox.y + (slotBox.height - height) / 2

                val x = columnX
                val y = columnY + (sprite.height + 10) * i

                sprite.draw(canvas, x, y)
            }
        }

        /**рисуем поверх слот автомат */
        slotMachine.draw(canvas)
    }

    /***
     * инициализация ресурсов
     */
    private fun initResources(canvas: Canvas) {


        /**
         * Загрузка "Золота"
         * Подгоняем под размеры и сохраняем как Sprite
         */
        val goldBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.gold)
        val goldWidth = canvas.width * 0.5f
        val goldHeight = canvas.width * 0.5f
        val goldY = canvas.height - goldWidth * 2

        gold = Sprite(
            Bitmap.createScaledBitmap(
                goldBitmap,
                goldWidth.toInt(),
                goldHeight.toInt(),
                true
            ), 0, goldY.toInt()
        )

        /**
         * Загрузка "Слот Автомат"
         * Подгоняем под размеры и сохраняем как Sprite
         */
        val slotMachineBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.slot)

        /**
         * Чтобы было удобно позицианизовать будем брать относительно высоты экрана
         * d - получаем отношение Ширины к Высоты исходного изображения
         */
        val d = slotMachineBitmap.width.toFloat() / slotMachineBitmap.height.toFloat()
        /**
         * высоту делаем 0.5 от высоты канваса
         */
        val slotMachineHeight = canvas.height.toFloat() * 0.5f
        /**
         * (0.5 от канваса) * отношение размера исходного изображения
         * получаем относительную ширину
         */
        val slotMachineWidth = slotMachineHeight * d
        /**
         * размещаем в левом нижнем угле
         */
        val slotMachineX = canvas.width - slotMachineWidth - 10
        val slotMachineY = canvas.height - slotMachineHeight * 1.2f

        slotMachine = Sprite(
            Bitmap.createScaledBitmap(
                slotMachineBitmap,
                slotMachineWidth.toInt(),
                slotMachineHeight.toInt(),
                true
            ), slotMachineX.toInt(), slotMachineY.toInt()
        )


        val slotBoxBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.slot_box)
        val slotBoxWidth = (slotMachineWidth * 0.53f).toInt()
        val slotBoxHeight = (slotMachineHeight * 0.18f).toInt()
        val slotBoxX = (slotMachineX + slotMachineWidth * 0.16f).toInt()
        val slotBoxY = (slotMachineY + slotMachineHeight * 0.38f).toInt()

        slotBox = Sprite(
            Bitmap.createScaledBitmap(slotBoxBitmap, slotBoxWidth, slotBoxHeight, true),
            slotBoxX,
            slotBoxY
        )

        val arr = arrayOf(R.drawable.item1, R.drawable.item2, R.drawable.item3)
        for (v in arr) {
            val bitmap = BitmapFactory.decodeResource(context.resources, v)
            val size = slotBoxWidth / 3 * 0.6
            val sprite =
                Sprite(Bitmap.createScaledBitmap(bitmap, size.toInt(), size.toInt(), true), 0, 0)
            items += sprite
        }
    }

    /***
     * Запускаем игру
     */
    fun spin(callback: () -> Unit) {
        state = State.SPIN

        /**
         * После 5000мс завершаем текущую игру
         */
        Handler().postDelayed({
            state = State.WAIT
            callback()
        }, 5000)
    }
}