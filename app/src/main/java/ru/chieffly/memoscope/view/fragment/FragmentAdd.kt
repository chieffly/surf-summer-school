package ru.chieffly.memoscope.view.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.presenters.AddPresenter
import ru.chieffly.memoscope.view.activity.MainActivity
import ru.chieffly.memoscope.view.dialogs.OpenImageDialogFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.app.Activity.RESULT_OK

const val REQUEST_CODE_GALLERY = 1
const val REQUEST_CODE_CAMERA = 2

class FragmentAdd : Fragment() {
    private val presenter = AddPresenter(this)
    private lateinit var activity : MainActivity
    private lateinit var currentPhotoPath: String

    private lateinit var btnClose: ImageButton
    private lateinit var txtMemTitle: TextView
    private lateinit var txtMemDescription: TextView
    private lateinit var btnSave: Button
    private lateinit var btnAddPicture: ImageButton
    private lateinit var buttonDeletePicture: ImageButton
    private lateinit var imgMem: ImageView
    private var imgPath = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_add, null)
        initViews(view)
        initListeners(view)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_GALLERY -> {
                val selectedImage: Uri? = data?.getData()
                setImage(selectedImage.toString())
                buttonDeletePicture.isVisible = true
            }
            REQUEST_CODE_CAMERA -> {
                if (resultCode == RESULT_OK) {
                    setImage(currentPhotoPath)
                    //TODO Сохранять и загружать фото с камеры
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("fragmentId",2 )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    fun setParent(activity: MainActivity) {
        this.activity = activity
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
                val imageLoadingVariants = arrayOf(getString(R.string.galery), getString(R.string.camera))

            val ImageDialog = AlertDialog.Builder(activity)
            ImageDialog.setTitle(getString(R.string.chose_pic))
                .setItems(imageLoadingVariants) { dialog, which ->
                    when (which) {
                        0 -> choosePhotoFromGallery()
                        1 -> takePhotoFromCamera()
                    }
                }
            ImageDialog.show()
        }
    }

    private fun choosePhotoFromGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        this.startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY)
    }

    private fun takePhotoFromCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Создаем файл для картинки
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {

            }
            println ("ЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕ "+photoFile.toString())
            // если с ним все ок, то идем дальше
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    activity,
                    "ru.chieffly.memoscope",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)

        currentPhotoPath = image.absolutePath
        return image
    }

     fun getMemTitle(): String {
        return txtMemTitle.text.toString()
    }

     fun getMemDescription(): String {
        return txtMemDescription.text.toString()
    }

     fun getImageLink(): String {
        return imgPath
    }

     fun drawImage() {
        Glide.with(requireContext()).load(getImageLink()).into(imgMem)
    }

     fun setImage(link: String) {
        imgPath = link
        drawImage()
        if (txtMemTitle.length() > 0) {
            btnSave.isEnabled = true
        }
    }
}