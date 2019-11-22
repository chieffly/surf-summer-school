package ru.chieffly.memoscope.view.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.view.main.fragments.REQUEST_CODE_CAMERA
import ru.chieffly.memoscope.view.main.fragments.REQUEST_CODE_GALLERY


class OpenImageDialogFragment : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(view)

        val dialogTitle = view?.findViewById(R.id.txtDialogTitle) as TextView
        val btnCam = view.findViewById(R.id.btnOne) as Button
        val btnGal = view.findViewById(R.id.btnTwo) as Button

        dialogTitle.text = getString(R.string.chose_pic)
        btnCam.text = getString(R.string.camera)
        btnGal.text = getString(R.string.galery)
        btnGal.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            targetFragment!!.startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY)

            dismiss()
        }
        btnCam.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                    takePictureIntent ->
                takePictureIntent.resolveActivity(activity?.packageManager)?.also {
                    targetFragment!!.startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                }
            }
            dismiss()

        }
        return builder.create()
    }


}