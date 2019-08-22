package ru.chieffly.memoscope

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    private var handler: Handler? = null
    private val DELAY: Long = 300

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        handler!!.postDelayed(mRunnable, DELAY)

    }

    public override fun onDestroy() {

        if (handler != null) {
            handler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}