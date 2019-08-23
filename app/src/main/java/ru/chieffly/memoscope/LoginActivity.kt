package ru.chieffly.memoscope

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    val passLength = 6

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

            val acc = LoginUserRequestDto(login_edit_text_phone.text.toString(), login_edit_text_pass.text.toString())
            client.registrationPost(acc).enqueue(object : Callback<AuthInfoDto> {
                override fun onFailure(call: Call<AuthInfoDto>?, t: Throwable?) {
                    val snackbar =
                        Snackbar.make(log_layout, getString(R.string.err_error), Snackbar.LENGTH_LONG)
                    snackbar.view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorError))
                    snackbar.show()
                    login_button.text = getString(R.string.log_in)
                    login_progressbar.visibility = ProgressBar.INVISIBLE
                }

                override fun onResponse(call: Call<AuthInfoDto>?, response: Response<AuthInfoDto>?) {
                    if (response?.isSuccessful()!!) {

                        login_button.text = getString(R.string.log_in)
                        login_progressbar.visibility = ProgressBar.INVISIBLE

                        val prefs = Prefs(getApplicationContext())
                        val userInfo = response?.body()?.userInfo
                        prefs.saveUser(userInfo!!)
                        response?.body()?.accessToken?.let { prefs.addString("Token", it) }

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        val snackbar =
                            Snackbar.make(log_layout, getString(R.string.err_wrong_data), Snackbar.LENGTH_LONG)
                        snackbar.view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorError))
                        snackbar.show()
                    }
                }
            })
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

}