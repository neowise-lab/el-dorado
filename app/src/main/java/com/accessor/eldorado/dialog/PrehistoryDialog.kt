package com.accessor.eldorado.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import com.accessor.eldorado.R

/**
 * Класс унаследованный от Dialog
 * Отображает предысторию
 */
class PrehistoryDialog(context: Context) : Dialog(context) {
    init {
        setContentView(R.layout.dialog_prehistory)
        val prehistoryText = findViewById<TextView>(R.id.prehistoryText)
        prehistoryText.movementMethod = ScrollingMovementMethod()
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}