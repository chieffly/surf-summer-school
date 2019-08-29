package ru.chieffly.memoscope.view.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.presenters.AddPresenter
import ru.chieffly.memoscope.view.activity.MainActivity
import ru.chieffly.memoscope.view.dialogs.OpenImageDialogFragment

const val REQUEST_CODE_GALLERY = 1
const val REQUEST_CODE_CAMERA = 2
const val REQUEST_CODE_DIALOG = 9

class FragmentAdd : Fragment() {
    private val presenter = AddPresenter(this)
    private lateinit var activity : MainActivity

    private lateinit var btnClose: ImageButton
    private lateinit var txtMemTitle: TextView
    private lateinit var txtMemDescription: TextView
    private lateinit var btnSave: Button
    private lateinit var btnAddPicture: ImageButton
    private lateinit var buttonDeletePicture: ImageButton
    private lateinit var imgMem: ImageView
    private var imgPath = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add, null)
        initViews(view)
        initListeners(view)
        return view
    }

    fun setParent(activity: MainActivity) {
        this.activity = activity
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_GALLERY -> {
                val selectedImage: Uri? = data?.getData()
                setImage(selectedImage.toString())
                buttonDeletePicture.isVisible = true
            }
            REQUEST_CODE_CAMERA -> {
                val imageBitmap = data?.getData() as Bitmap
                //TODO Сохранять и загружать фото с камеры
            }
        }
    }


    private fun initViews(view: View) {
        btnClose = view.findViewById(R.id.btnClose)
        btnAddPicture = view.findViewById(R.id.btnAddPicture)
        buttonDeletePicture = view.findViewById(R.id.buttonDeletePicture)
        buttonDeletePicture.isVisible = false

        btnSave = view.findViewById(R.id.btnSaveMem)
        btnSave.isEnabled = false
        txtMemTitle = view.findViewById(R.id.txtMemTitle)
        txtMemDescription = view.findViewById(R.id.txtMemDescription)
        imgMem = view.findViewById(R.id.imgMem)

    }

    private fun closeTab() {
        activity.showFragmentDash()
    }

    private fun initListeners(view: View) {
        txtMemTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (txtMemTitle.length() < 0) {
                    btnSave.isEnabled = false
                } else {
                    if (imgPath != "") {
                        btnSave.isEnabled = true
                    }
                }
            }
        })

        btnClose.setOnClickListener {
            closeTab()
        }
        btnSave.setOnClickListener {
            presenter.onSaveMem()
            closeTab()
        }
        buttonDeletePicture.setOnClickListener {
            imgMem.setImageBitmap(null)
            buttonDeletePicture.isVisible = false
            imgPath=""
        }
        btnAddPicture.setOnClickListener {
            val dialog = OpenImageDialogFragment()
            dialog.setTargetFragment(this, REQUEST_CODE_DIALOG)
            dialog.show(fragmentManager!!.beginTransaction(), "dialog")
        }
    }

    fun getMemTitle(): String {
        return txtMemTitle.getText().toString()
    }

    fun getMemDescription(): String {
        return txtMemDescription.getText().toString()
    }

    fun getImageLink(): String {
        return imgPath
    }

    private fun drawImage() {
        Glide.with(requireContext()).load(getImageLink()).into(imgMem)
    }

    private fun setImage(link: String) {
        imgPath = link
        drawImage()
        if (txtMemTitle.length() > 0) {
            btnSave.isEnabled = true
        }
    }
}