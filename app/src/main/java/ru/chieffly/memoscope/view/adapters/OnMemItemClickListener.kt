package ru.chieffly.memoscope.view.adapters

import android.widget.ImageView
import ru.chieffly.memoscope.model.MemDto

interface OnMemItemClickListener {

    fun onMemItemClick(pos: Int, mem: MemDto, shareImageView: ImageView)

}