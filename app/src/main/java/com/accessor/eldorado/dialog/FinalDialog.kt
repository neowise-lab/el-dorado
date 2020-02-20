package com.accessor.eldorado.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.accessor.eldorado.R

/**
 * Класс унаследованный от Dialog
 * Отображает предысторию
 **/
class FinalDialog(context: Context) : Dialog(context) {
    init {
        setContentView(R.layout.dialog_final)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}