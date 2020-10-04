package com.accessor.eldorado

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.accessor.eldorado.dialog.FinalDialog
import com.accessor.eldorado.game.DrawView
import com.accessor.eldorado.util.fullscreenMode
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private lateinit var drawView: DrawView

    /**Количество игр*/
    private var clickCount: Int = 5

    /**Флаг ожидания окочания игры*/
    private var waitOfCompletionGame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.fullscreenMode()

        setContentView(R.layout.activity_game)

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
        /**Визуально делаем кнопку недоступным*/
        disableGame()

        /**Говорим игровому потоку что игра началась*/
        drawView.spin {
            /**
             * При окончании игры снимаем блокировку
             * Возвращаем кнопку обратно
             * */
            enableGame()
            /**Если счетчик меньше 1 вызываем финальный диалог*/
            if (clickCount < 1) {
                FinalDialog(this).show()
            }
        }
    }

    private fun disableGame() {
        waitOfCompletionGame = true
        spinButton.isEnabled = false
        spinButton.alpha = 0.4f
    }

    private fun enableGame() {
        waitOfCompletionGame = false
        spinButton.isEnabled = true
        spinButton.alpha = 1f
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