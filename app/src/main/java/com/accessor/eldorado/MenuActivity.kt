package com.accessor.eldorado

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.accessor.eldorado.dialog.PrehistoryDialog
import com.accessor.eldorado.util.fullscreenMode

/**
 * Меню приложения
 */
class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.fullscreenMode()

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
        openBrowser("http://wwww.vk.com/j_imomaddinov")
    }

    /** Метод вызывается при нажатии на кнопку GITHUB в меню*/
    fun gotoGithub(view: View) {
        openBrowser("http://www.github.com/accessor-code/El-Dorado")
    }

    fun openBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}