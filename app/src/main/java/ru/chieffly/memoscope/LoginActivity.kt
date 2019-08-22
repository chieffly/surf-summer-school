package ru.chieffly.memoscope

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val passLength = 6
    private var handler: Handler? = null
    private val DELAY: Long = 1000

    val client by lazy {
        NetworkService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_edit_text_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                login_text_layout_phone.error = ""
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        login_edit_text_pass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                login_text_layout_pass.error = ""
                login_text_layout_pass.helperText = ""
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (login_edit_text_pass.length() < passLength) {
                    login_text_layout_pass.helperText = getString(R.string.pass_length, "" + passLength)
                } else {
                    login_text_layout_pass.helperText = ""
                }
            }
        })
    }

    fun onClick(view: View) {
        login_text_layout_phone.error = ""
        login_text_layout_pass.error = ""
        login_text_layout_pass.helperText = ""

        if (!hasEmptyFields()) {
            login_button.text = ""
            login_progressbar.visibility = ProgressBar.VISIBLE
            handler = Handler()
            handler!!.postDelayed(mRunnable, DELAY)
        }
    }

    fun hasEmptyFields(): Boolean {
        var fail = false;
        if (login_edit_text_phone.length() == 0) {
            login_text_layout_phone.error = getString(R.string.err_empty)
            fail = true
        }
        if (login_edit_text_pass.length() < passLength) {
            if (login_edit_text_pass.length() == 0)
                login_text_layout_pass.error = getString(R.string.err_empty)
            else
                login_text_layout_pass.error = getString(R.string.pass_length, "" + passLength)
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