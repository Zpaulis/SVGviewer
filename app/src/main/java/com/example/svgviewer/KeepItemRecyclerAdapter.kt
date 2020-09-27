package com.example.svgviewer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_keep_image.view.*

class KeepItemRecyclerAdapter(
    private val listener: AdapterClickListener,
    private val infos: MutableList<CountryInfo>
) :
    RecyclerView.Adapter<KeepItemRecyclerAdapter.KeepViewHolder>() {

    class KeepViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeepViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_keep_image, parent, false)
        return KeepViewHolder(inflater)
    }

    override fun getItemCount() = infos.size

    override fun onBindViewHolder(holder: KeepViewHolder, position: Int) {
        val context = holder.itemView.context
        val info = infos[position]
        holder.itemView.country_name.text = info.name
        holder.itemView.flag_url_string.text = info.flag

        holder.itemView.keepClose.setOnClickListener {
            val currentPosition = infos.indexOf(info)
            infos.removeAt(currentPosition)
            notifyItemRemoved(currentPosition)
            Toast.makeText(holder.itemView.context, info.name, Toast.LENGTH_LONG).show()
        }

        holder.itemView.setOnClickListener {
            listener.itemClicked(infos[position])
            Log.d("TTTTTTTTTTTTTTTTTTT",infos[position].name)

        }
    }
}