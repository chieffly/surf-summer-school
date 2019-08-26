package ru.chieffly.memoscope.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ru.chieffly.memoscope.R

private const val DELAY: Long = 300

class SplashActivity : AppCompatActivity() {
    private var handler: Handler = Handler()

    var runnable: Runnable = object : Runnable {
        override fun run() {
            openMainScreen()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed(runnable, DELAY)
    }

    public override fun onDestroy() {

        handler?.let {
            it.removeCallbacks(runnable)
        }
        super.onDestroy()
    }

    fun openLoginScreen() {

        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun openMainScreen() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}