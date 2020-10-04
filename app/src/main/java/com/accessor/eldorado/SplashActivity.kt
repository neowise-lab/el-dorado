package com.accessor.eldorado

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.accessor.eldorado.util.fullscreenMode

/**
 * Экран загруки
 * Точка запуска приложения
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.fullscreenMode()
        setContentView(R.layout.activity_splash)

        /** Подождем 4000мс пока крутится монетка*/
        /** После запускам меню т.е. MenuActivity*/
        Handler().postDelayed(::showMainActivity, 4000)
    }

    private fun showMainActivity() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}