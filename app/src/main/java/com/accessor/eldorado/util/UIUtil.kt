package com.accessor.eldorado.util

import android.app.Activity
import android.view.Window
import android.view.WindowManager

fun Activity.fullscreenMode() {
    this.window.requestFeature(Window.FEATURE_NO_TITLE)
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}