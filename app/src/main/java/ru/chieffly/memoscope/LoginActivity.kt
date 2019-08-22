package ru.chieffly.memoscope

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val passLength = 6
    private lateinit var textLayoutPhone: TextInputLayout
    private lateinit var texteditPhone: TextInputEditText
    private lateinit var textLayoutPass: TextInputLayout
    private lateinit var texteditPass: TextInputEditText
    private var handler: Handler? = null
    private val DELAY: Long = 1000


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            textLayoutPhone = findViewById<TextInputLayout>(R.id.login_text_layout_phone)
            texteditPhone = findViewById<TextInputEditText>(R.id.login_edit_text_phone)
            textLayoutPass = findViewById<TextInputLayout>(R.id.login_text_layout_pass)
            texteditPass = findViewById<TextInputEditText>(R.id.login_edit_text_pass)

            texteditPhone.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    textLayoutPhone.error = ""
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

            texteditPass.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    textLayoutPass.error = ""
                    textLayoutPass.helperText = ""

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (texteditPass.length() < passLength) {
                        textLayoutPass.helperText = getString(R.string.pass_length,""+passLength)
                    } else {
                        textLayoutPass.helperText = ""
                    }
                }
            })
        }

    fun onClick(view: View ) {
        textLayoutPhone.error = ""
        textLayoutPass.helperText = ""
        textLayoutPass.error = ""


        if (!hasEmptyFields()) {
            var loginButton = findViewById<Button>(R.id.login_button)

            var progressBar = findViewById<ProgressBar>(R.id.login_progressbar)
            loginButton.text = ""
            progressBar.visibility = ProgressBar.VISIBLE

            handler = Handler()
            handler!!.postDelayed(mRunnable, DELAY)

        }
    }

    fun hasEmptyFields () : Boolean {
        var fail = false;
        if (texteditPhone.length() == 0 ) {
            textLayoutPhone.error = getString(R.string.err_empty)
            fail = true
        }
        if (texteditPass.length() < passLength) {
            if (texteditPass.length() == 0 )
                textLayoutPass.error = getString(R.string.err_empty)
            else
            textLayoutPass.error = getString(R.string.pass_length,""+passLength)
            fail = true
        }


        return fail
    }

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            login_button.text = getString(R.string.log_in)
            login_progressbar.visibility = ProgressBar.INVISIBLE
        }
    }
}