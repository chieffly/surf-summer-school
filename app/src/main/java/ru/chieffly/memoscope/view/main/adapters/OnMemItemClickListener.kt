package ru.chieffly.memoscope.view.main.adapters

import android.widget.ImageView
import ru.chieffly.memoscope.model.MemDto

interface OnMemItemClickListener {

    fun onMemItemClick(pos: Int, mem: MemDto, shareImageView: ImageView)

}