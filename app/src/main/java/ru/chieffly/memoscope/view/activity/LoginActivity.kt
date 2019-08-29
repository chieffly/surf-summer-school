package ru.chieffly.memoscope.view.activity

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.presenters.ERR_EMPTY
import ru.chieffly.memoscope.presenters.ERR_WRONGDATA
import ru.chieffly.memoscope.presenters.LoginPresenter

private const val PASS_MIN_LENGTH = 6

class LoginActivity : AppCompatActivity() {
    private val presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners ()
    }

    private fun hasEmptyFields(): Boolean {
        var fail = false;
        if (txtPhone.length() == 0) {
            inputLayoutPhone.error = getString(R.string.err_empty)
            fail = true
        }
        if (txtPass.length() < PASS_MIN_LENGTH) {
            if (txtPass.length() == 0)
                inputLayoutPass.error = getString(R.string.err_empty)
            else
                inputLayoutPass.error = getString(R.string.pass_length, "" + PASS_MIN_LENGTH)
            fail = true
        }
        return fail
    }

    private fun initListeners () {
        txtPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        txtPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?)
            {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                clearHelpersPhone ()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {}
        })

        txtPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                clearHelpersPass ()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {}
            override fun afterTextChanged(p0: Editable?) {
                if (txtPass.length() < PASS_MIN_LENGTH) {
                    inputLayoutPass.helperText = getString(R.string.pass_length, "" + PASS_MIN_LENGTH)
                }
            }
        })

        btnLogin.setOnClickListener {
            inputLayoutPhone.error = ""
            clearHelpersPhone ()
            if (!hasEmptyFields()) {
                presenter.onButtonLoginClicked()
            }
        }
    }

    private fun clearHelpersPhone () {
        inputLayoutPhone.error = ""
        inputLayoutPhone.helperText = ""
    }

    private fun clearHelpersPass () {
        inputLayoutPass.error = ""
        inputLayoutPass.helperText = ""
    }

    fun showButtonProgress () {
        btnLogin.isVisible = false
        progressbarLogin.isVisible = true
    }

    fun hideButtonProgress () {
        btnLogin.isVisible = true
        progressbarLogin.isVisible = false
    }

    fun getPassField():String {
        return txtPass.text.toString()
    }

    fun getPhoneField():String {
        return txtPhone.text.toString()
    }


    fun showErroSnack(errorType : Int) {
        val errorText =
            when (errorType) {
            ERR_WRONGDATA -> getString(R.string.err_wrong_data)
            ERR_EMPTY -> getString(R.string.err_empty)
            else -> getString(R.string.err_error)
        }

        val snackbar =Snackbar.make(log_layout,
            errorText, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(applicationContext,R.color.colorError))
        snackbar.show()
    }

    fun openMainScreen () {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}