package app.olauncher.helper

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.Button

class ButtonWithTimer(
    val button: Button,
    val text: String,
    val duration: Int) {

    lateinit var timer: CountDownTimer
    fun startTimer() {
        button.isEnabled = false
        timer = object : CountDownTimer(duration * 1000L, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisLeft: Long) {
                val secondsLeft = millisLeft / 1000
                button.text = "$text ($secondsLeft)"
            }
            override fun onFinish() {
                button.text = text
                button.isEnabled = true
            }
        }
        timer.start()
    }

    fun cancelTimer() {
        timer.cancel()
    }
}