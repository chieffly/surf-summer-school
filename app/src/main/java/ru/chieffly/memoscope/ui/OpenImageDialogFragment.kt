package ru.chieffly.memoscope.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ru.chieffly.memoscope.R


class OpenImageDialogFragment : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(view)

        val dialogTitle = view?.findViewById(R.id.txtDialogTitle) as TextView
        val btnCam = view?.findViewById(R.id.btnOne) as Button
        val btnGal = view?.findViewById(R.id.btnTwo) as Button

        dialogTitle.text = getString(R.string.chose_pic)
        btnCam.text = getString(R.string.camera)
        btnGal.text = getString(R.string.galery)
        btnGal.setOnClickListener{
            val intent = Intent()
            //intent.putExtra("imagePath", "\\somewhere")
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            dismiss()
        }

        return builder.create()
    }

}