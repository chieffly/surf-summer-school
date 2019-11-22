package ru.chieffly.memoscope.view.splash

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.utils.UserStorage
import ru.chieffly.memoscope.view.login.LoginActivity
import ru.chieffly.memoscope.view.main.MainActivity

private const val DELAY: Long = 300

class SplashActivity : AppCompatActivity() {
    private var handler: Handler = Handler()

    var runnable: Runnable = object : Runnable {
        override fun run() {
            val storage = UserStorage(applicationContext)
            if (storage.getToken() !="null") {
                openMainScreen()
            } else  {openLoginScreen()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val drawable = getResources().getDrawable(
            R.drawable.avd_anim
        ) as AnimatedVectorDrawable
        splash_logo.setImageDrawable(drawable)
        drawable.start()

        handler.postDelayed(runnable, DELAY)
    }

    public override fun onDestroy() {

        handler.let {
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