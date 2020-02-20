package com.accessor.eldorado

import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.accessor.eldorado.dialog.FinalDialog
import com.accessor.eldorado.game.DrawView

class GameActivity : AppCompatActivity() {

    private lateinit var drawSurface: SurfaceView
    private lateinit var clickCountText: TextView
    private lateinit var drawView: DrawView
    private lateinit var spinButton: Button

    /**Количество игр*/
    private var clickCount: Int = 5

    /**Флаг ожидания окочания игры*/
    private var waitOfCompletionGame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_game)

        drawSurface = findViewById(R.id.drawSurface)
        clickCountText = findViewById(R.id.clickCountText)
        spinButton = findViewById(R.id.spinButton)

        drawView = DrawView(this, drawSurface.holder)
        updateClickCountText()
    }

    fun spinAction(v: View) {

        /**Если игра идет, то будем игнорировать нажатия*/
        if (waitOfCompletionGame) return

        /**Уменьшаем счетчик*/
        clickCount--

        updateClickCountText()
        /**Блокируем нажатия*/
        waitOfCompletionGame = true
        /**Визуально делаем кнопку недоступным*/
        spinButton.isEnabled = false
        spinButton.alpha = 0.4f

        /**Говорим игровому потоку что игра началась*/
        drawView.spin {

            /**
             * При окончании игры снимаем блокировку
             * Возвращаем кнопку обратно
             * */
            waitOfCompletionGame = false
            spinButton.isEnabled = true
            spinButton.alpha = 1f

            /**Если счетчик меньше 1 вызываем финальный диалог*/
            if (clickCount < 1) {
                FinalDialog(this).show()
            }
        }
    }

    /**Обновляем счетчик*/
    private fun updateClickCountText() {
        clickCountText.text = "$clickCount"
    }

    /** Финальный диалог, кнопка окей -> выкидываем на главное меню*/
    fun okAction(view: View) {
        finish()
    }

    fun stopGame(view: View) {
        drawView.stop()
        finish()
    }
}