package com.example.svgviewer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_keep_image.view.*
import kotlin.coroutines.coroutineContext


class KeepItemRecyclerAdapter(
    private val listener: AdapterClickListener,
    private val infos: MutableList<CountryInfo>
) :
    RecyclerView.Adapter<KeepItemRecyclerAdapter.KeepViewHolder>() {

    abstract class KeepViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(position: Int)
    }

    inner class ImageViewHolder(view: View) : KeepViewHolder(view) {
        override fun bind(position: Int) {
//            val info = infos[position]
//            itemView.country_name.text = info.name
 //             Glide.with(itemView)
//                .load(info.uri)
//                .into(itemView.keepImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            IMAGE_KEEP -> ImageViewHolder(inflater.inflate(R.layout.item_keep_image, parent, false))
            else -> ImageViewHolder(inflater.inflate(R.layout.item_keep_image, parent, false))
        }
    }

    override fun getItemViewType(position: Int) = IMAGE_KEEP

    override fun getItemCount() = infos.size

    override fun onBindViewHolder(holder: KeepViewHolder, position: Int) {
        holder.bind(position)
        val info = infos[position]

        holder.itemView.keepClose.setOnClickListener {
            val currentPosition = infos.indexOf(info)
            infos.removeAt(currentPosition)
            notifyItemRemoved(currentPosition)
        }

        holder.itemView.setOnClickListener {
            listener.itemClicked(infos[position])
            Log.d("TTTTTTTTTTTTTTTTTTT",infos[position].name)

        }

    }

    companion object {
        private const val IMAGE_KEEP = 1
    }

}