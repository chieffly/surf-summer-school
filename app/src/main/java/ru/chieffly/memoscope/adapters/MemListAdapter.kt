package ru.chieffly.memoscope.adapters

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.model.MemDto
import com.bumptech.glide.Glide
import androidx.core.content.ContextCompat.startActivity
import ru.chieffly.memoscope.activity.MainActivity
import android.content.Intent
import ru.chieffly.memoscope.activity.MemBrowserActivity


class MemListAdapter(var mems: List<MemDto>) : RecyclerView.Adapter<MemListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_mem, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = mems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(mems[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(mem: MemDto) {
            val textViewName = itemView.findViewById(R.id.mem_title) as TextView
            val imageView = itemView.findViewById(R.id.mem_image) as ImageView
            val btnLike = itemView.findViewById(R.id.buttonLike) as ImageButton
            btnLike.setImageResource(
                if (mem.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border)
            val btnShare = itemView.findViewById(R.id.buttonShare) as ImageButton
            textViewName.text = mem.title
            imageView.setOnClickListener({
                val intent = Intent(itemView.context, MemBrowserActivity::class.java)
                intent.putExtra("MEM", mem)
                //startActivity(itemView.context,intent)
                itemView.context.startActivity(intent)
            })
            btnShare.setOnClickListener({
                })
            btnLike.setOnClickListener({
                mem.isFavorite = !mem.isFavorite
                if (mem.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border
            })
            Glide.with(itemView).load(mem.photoUtl).into(imageView)
        }
    }

}
