package com.accessor.eldorado

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * Экран загруки
 * Точка запуска приложения
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** Делаем полный экран*/
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_splash)

        /** Подождем 4000мс пока крутится монетка*/
        /** После запускам меню т.е. MenuActivity*/
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MenuActivity::class.java))
            finish()
        }, 4000)
    }
}