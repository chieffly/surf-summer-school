package ru.chieffly.memoscope.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.user.UserStorage
import ru.chieffly.memoscope.model.AuthInfoDto
import ru.chieffly.memoscope.model.LoginUserRequestDto
import ru.chieffly.memoscope.net.NetworkService

private const val PASS_LENGTH = 6

class LoginActivity : AppCompatActivity() {

    val ns = NetworkService.instance


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners ()
    }

    fun hasEmptyFields(): Boolean {
        var fail = false;
        if (login_edit_text_phone.length() == 0) {
            login_text_layout_phone.error = getString(R.string.err_empty)
            fail = true
        }
        if (login_edit_text_pass.length() < PASS_LENGTH) {
            if (login_edit_text_pass.length() == 0)
                login_text_layout_pass.error = getString(R.string.err_empty)
            else
                login_text_layout_pass.error = getString(R.string.pass_length, "" + PASS_LENGTH)
            fail = true
        }
        return fail
    }

    fun initListeners () {
        login_edit_text_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                login_text_layout_phone.error = ""
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        login_edit_text_pass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                login_text_layout_pass.error = ""
                login_text_layout_pass.helperText = ""
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (login_edit_text_pass.length() < PASS_LENGTH) {
                    login_text_layout_pass.helperText = getString(R.string.pass_length, "" + PASS_LENGTH)
                } else {
                    login_text_layout_pass.helperText = ""
                }
            }
        })

        login_button.setOnClickListener {
            login_text_layout_phone.error = ""
            login_text_layout_pass.error = ""
            login_text_layout_pass.helperText = ""

            if (!hasEmptyFields()) {
                login_button.text = ""
                login_progressbar.isVisible = true
                makeRequest ()
            }
        }
    }

    fun makeRequest () {
        val acc = LoginUserRequestDto(
            login_edit_text_phone.text.toString(),
            login_edit_text_pass.text.toString()
        )
        ns.getAuthApi().registrationPost(acc).enqueue(object : Callback<AuthInfoDto> {
            override fun onFailure(call: Call<AuthInfoDto>?, t: Throwable?) {
                val snackbar =
                    Snackbar.make(log_layout, getString(R.string.err_error), Snackbar.LENGTH_LONG)
                snackbar.view.setBackgroundColor(ContextCompat.getColor(applicationContext,
                    R.color.colorError
                ))
                snackbar.show()
                login_button.text = getString(R.string.log_in)
                login_progressbar.isVisible = false
            }

            override fun onResponse(call: Call<AuthInfoDto>?, response: Response<AuthInfoDto>?) {
                if (response?.isSuccessful()!!) {

                    login_button.text = getString(R.string.log_in)
                    login_progressbar.isVisible = false
                    saveAuth (response)
                    openMainScreen ()

                } else {

                    val snackbar =
                        Snackbar.make(log_layout, getString(R.string.err_wrong_data), Snackbar.LENGTH_LONG)
                    snackbar.view.setBackgroundColor(ContextCompat.getColor(applicationContext,
                        R.color.colorError
                    ))
                    snackbar.show()
                }
            }
        })
    }

    fun saveAuth (response:  Response<AuthInfoDto> ) {
        val storage = UserStorage(applicationContext)
        response?.body()?.let {storage.saveAuthorization(it) }
    }

    fun openMainScreen () {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}