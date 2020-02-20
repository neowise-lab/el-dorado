package com.accessor.eldorado

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.accessor.eldorado.dialog.PrehistoryDialog

/**
 * Меню приложения
 */
class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Делаем полный экран*/
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_menu)
    }

    /** Метод вызывается при нажатии на кнопку START в меню*/
    fun startAction(view: View) {
        /** Запускаем Игру, т.е. GameActivity*/
        val intent = Intent(this@MenuActivity, GameActivity::class.java)
        startActivity(intent)
    }

    /** Метод вызывается при нажатии на кнопку PREHISTORY в меню*/
    fun prehistoryAction(view: View) {
        /** Запускаем диалоговое окно с предысторией*/
        val dialog = PrehistoryDialog(this)
        dialog.show()
    }

    /** Метод вызывается при нажатии на кнопку VK в меню*/
    fun gotoVK(view: View) {
        /** Запускаем браузер*/
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("http://wwww.vk.com/j_imomaddinov"))
        startActivity(browserIntent)
    }

    /** Метод вызывается при нажатии на кнопку GITHUB в меню*/
    fun gotoGithub(view: View) {
        /** Запускаем браузер*/
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/accessor-code/El-Dorado"))
        startActivity(browserIntent)
    }
}