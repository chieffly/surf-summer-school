package ru.chieffly.memoscope.view.main.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.db.MemDao
import ru.chieffly.memoscope.model.MemDto
import javax.inject.Inject


class MemRecyclerAdapter(var mems: List<MemDto>, var om: OnMemItemClickListener) : RecyclerView.Adapter<MemRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_mem, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = mems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(mems[position])
        holder.itemView.setOnClickListener{
            om.onMemItemClick(holder.getAdapterPosition(), mems[position], holder.mem_image)

        }


    }

    fun updateList(newList : List<MemDto>){
        this.mems = newList
        //the most important here
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @Inject
        lateinit var memDb: MemDao

        @Inject
        lateinit var app: MyApp
        init {
            MyApp.appComponent.inject(viewModel = this)
        }
        lateinit var mem_image : ImageView
        lateinit var textViewName : TextView

        fun bindItems(mem: MemDto) {
             textViewName = itemView.findViewById(R.id.mem_title) as TextView
            mem_image = itemView.findViewById(R.id.mem_image) as ImageView
            val btnLike = itemView.findViewById(R.id.buttonLike) as ImageButton
            val btnShare = itemView.findViewById(R.id.buttonShare) as ImageButton
            btnLike.setImageResource(
                if (mem.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border)
            textViewName.text = mem.title

            btnShare.setOnClickListener {
                shareMem(mem)
            }
            btnLike.setOnClickListener {
                mem.isFavorite = !mem.isFavorite
                btnLike.setImageResource(
                if (mem.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border)
                Single.fromCallable {
                    memDb.update(mem)
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe()            }
            Glide.with(itemView).load(mem.photoUtl).into(mem_image)
        }

        fun shareMem (mem: MemDto) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, mem.title + " \n" + mem.description + "\n"+mem.photoUtl)
            shareIntent.type = "text/plain"
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val chooser = Intent.createChooser(shareIntent,app.getString(R.string.share_menu))
            itemView.context.startActivity(chooser)
        }
    }

}
