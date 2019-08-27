package ru.chieffly.memoscope.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.db.AppDatabase
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.ui.OpenImageDialogFragment
import java.util.*

class FragmentAdd : Fragment() {
    lateinit var btnClose : ImageButton
    lateinit var txtMemTitle : TextView
    lateinit var txtMemDescription : TextView
    lateinit var btnSave : Button
    lateinit var btnAddPicture : ImageButton
    lateinit var buttonDeletePicture : ImageButton
    lateinit var imgMem : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add, null)
        initViews(view)
        initListeners(view)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(resultCode) {
            Activity.RESULT_OK -> addImage ()
            Activity.RESULT_CANCELED-> println("NOT OK")
        }
    }

    private fun initViews(view : View) {
        btnClose = view.findViewById(R.id.btnClose)
        btnAddPicture = view.findViewById(R.id.btnAddPicture)
        buttonDeletePicture = view.findViewById(R.id.buttonDeletePicture)
        btnSave = view.findViewById(R.id.btnSaveMem)
        txtMemTitle = view.findViewById(R.id.txtMemTitle)
        txtMemDescription = view.findViewById(R.id.txtMemDescription)
        imgMem = view.findViewById(R.id.imgMem)

    }

    private fun initListeners(view : View) {
        btnClose.setOnClickListener {
            val ftrans = fragmentManager!!.beginTransaction()
            ftrans.replace(R.id.fragment_container, FragmentDash())
            ftrans.commit()}
        btnSave.setOnClickListener {

            val mem = MemDto(
                0,
                txtMemTitle.getText().toString(),
                txtMemDescription.getText().toString(),
                false,
                Date().time / 1000,
                "https://miro.medium.com/max/556/1*A9ekuHD-z2SVao5rT8ZEUQ.jpeg",
                3232112
            )
            val db = AppDatabase.getDatabase(requireContext())
            val memDB = db.memDao()
            memDB.insert(mem)
            val ftrans = fragmentManager!!.beginTransaction()
            ftrans.replace(R.id.fragment_container, FragmentDash())
            ftrans.commit()
            }
        buttonDeletePicture.setOnClickListener {
            }
        btnAddPicture.setOnClickListener {
            val dialog = OpenImageDialogFragment()
            dialog.setTargetFragment(this, 333)
            dialog.show(fragmentManager!!.beginTransaction(), "dialog")
        }
    }


    private fun addImage () {
        Glide.with(requireContext()).load(R.drawable.i).into(imgMem)
    }
}