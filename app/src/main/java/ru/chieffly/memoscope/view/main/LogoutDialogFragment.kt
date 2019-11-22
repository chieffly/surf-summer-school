package ru.chieffly.memoscope.view.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.view.login.LoginActivity


class LogoutDialogFragment : DialogFragment() {

    private val presenter = LogoutDialogPresenter(this)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(view)

        val dialogTitle = view?.findViewById(R.id.txtDialogTitle) as TextView
        val btnYes = view.findViewById(R.id.btnOne) as Button
        val btnNo = view.findViewById(R.id.btnTwo) as Button

        dialogTitle.text = getString(R.string.logoutTitle)
        btnYes.text = getString(R.string.logoutConfirm)
        btnNo.text = getString(R.string.logoutCancel)
        btnNo.setOnClickListener {
            dismiss()
        }
        btnYes.setOnClickListener {
            presenter.logoutRequest()

        }
        return builder.create()
    }

    fun exitToLoginScreen ()
    {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}