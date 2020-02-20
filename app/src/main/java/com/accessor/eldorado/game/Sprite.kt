package com.accessor.eldorado.game

import android.graphics.Bitmap
import android.graphics.Canvas

/**
 * Спрайт
 */
class Sprite(private val image: Bitmap, var x: Int = 0, var y: Int = 0) {

    val width = image.width
    val height = image.height

    fun draw(canvas: Canvas, x: Int = this.x, y: Int = this.y) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }
}