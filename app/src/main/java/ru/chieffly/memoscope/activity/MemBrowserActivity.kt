package ru.chieffly.memoscope.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_membrowser.*
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.user.APP_PREFERENCES_USERNAME
import ru.chieffly.memoscope.user.UserStorage
import java.util.*


class MemBrowserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membrowser)

        val mem = intent.getSerializableExtra("MEM") as MemDto
        initViews(mem)
    }

    fun initViews(mem: MemDto) {
        btnLikeDetail.setImageResource(
            if (mem.isFavorite) R.drawable.ic_favorite
            else R.drawable.ic_favorite_border)

        textmemTitle.text = mem.title
        val currentDate = Date().time / 1000
        val daysAgo = (currentDate - mem.createdDate) / (60 * 60 * 24)
        textMemDate.text =  getString(R.string.mem_date, "" + daysAgo)
        textMemDescription.text = mem.description
        val storage = UserStorage(this)
        txtUserName.text = storage.getField(APP_PREFERENCES_USERNAME)

        btnClose.setOnClickListener {
            finish()
        }
        Glide.with(this).load(mem.photoUtl).into(imageView)
        btnLikeDetail.setOnClickListener {
            mem.isFavorite = !mem.isFavorite
            btnLikeDetail.setImageResource(
                if (mem.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border)
        }
    }
}