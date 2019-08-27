package ru.chieffly.memoscope.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.activity.LoginActivity
import ru.chieffly.memoscope.activity.MainActivity
import ru.chieffly.memoscope.model.AuthInfoDto
import ru.chieffly.memoscope.net.NetworkService
import ru.chieffly.memoscope.user.UserStorage


class LogoutDialogFragment : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(view)

        val dialogTitle = view?.findViewById(R.id.txtDialogTitle) as TextView
        val btnYes = view?.findViewById(R.id.btnOne) as Button
        val btnNo = view?.findViewById(R.id.btnTwo) as Button

        dialogTitle.text = getString(R.string.logoutTitle)
        btnYes.text = getString(R.string.logoutConfirm)
        btnNo.text = getString(R.string.logoutCancel)
        btnNo.setOnClickListener {
            dismiss()
        }
        btnYes.setOnClickListener {
            val ns = NetworkService.instance

            ns.getAuthApi().logoutRequest().enqueue(object : Callback<AuthInfoDto> {
                override fun onFailure(call: Call<AuthInfoDto>?, t: Throwable?) {
                    println("fail ")
                }

                override fun onResponse(call: Call<AuthInfoDto>?, response: Response<AuthInfoDto>?) {
                    if (response?.isSuccessful()!!) {
                        val storage = UserStorage(requireContext())
                        storage.saveToken("null")
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                    }
                }
            })


        }

        return builder.create()
    }

}