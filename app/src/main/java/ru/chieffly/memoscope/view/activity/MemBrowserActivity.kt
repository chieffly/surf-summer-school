package ru.chieffly.memoscope.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_membrowser.*
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.presenters.MemBrowserPresenter

const val SECONDS_IN_DAY = 86400
const val MEM_SERIALIZE_ID = "Mem"

class MemBrowserActivity : AppCompatActivity() {

    private val presenter = MemBrowserPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membrowser)

        val mem = intent.getSerializableExtra(MEM_SERIALIZE_ID) as MemDto
        setData(mem)
    }

    fun setData(mem: MemDto) {
        btnLikeDetail.setImageResource(
            if (mem.isFavorite) R.drawable.ic_favorite
            else R.drawable.ic_favorite_border)
        textmemTitle.text = mem.title
            val daysAgo = (presenter.getCurentData () - mem.createdDate) / SECONDS_IN_DAY
        textMemDate.text =  getString(R.string.mem_date, "" + daysAgo)
        textMemDescription.text = mem.description
        txtUserName.text = presenter.getUserName()
        Glide.with(this).load(mem.photoUtl).into(mem_image)

        btnClose.setOnClickListener {
            finish()
        }
        btnLikeDetail.setOnClickListener {
            mem.isFavorite = !mem.isFavorite
            btnLikeDetail.setImageResource(
                if (mem.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border)
            presenter.onUpdateMem(mem)
        }

        btnShare.setOnClickListener {
            presenter.shareMem(mem)
        }
    }
}