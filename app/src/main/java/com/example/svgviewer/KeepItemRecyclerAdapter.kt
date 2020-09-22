package com.example.svgviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_keep_image.view.*


class KeepItemRecyclerAdapter(private val items: MutableList<MainActivity.KeepItemImage>) :
    RecyclerView.Adapter<KeepItemRecyclerAdapter.KeepViewHolder>() {

    abstract class KeepViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(position: Int)
    }

    inner class ImageViewHolder(view: View) : KeepViewHolder(view) {
        override fun bind(position: Int) {
            val item = items[position] as MainActivity.KeepItemImage
            Glide.with(itemView)
                .load(item.uri)
                .into(itemView.keepImage)
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

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: KeepViewHolder, position: Int) {
        holder.bind(position)
        val item = items[position]

        holder.itemView.keepClose.setOnClickListener {
            val currentPosition = items.indexOf(item)
            items.removeAt(currentPosition)
            notifyItemRemoved(currentPosition)
        }
    }

    companion object {
        private const val IMAGE_KEEP = 1
    }

}