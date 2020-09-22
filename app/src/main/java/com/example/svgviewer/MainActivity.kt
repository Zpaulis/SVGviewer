package com.example.svgviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    data class KeepItemImage(val uri: String)

    val items
        get() = MutableList(1) { imageItem }
    val imageItem get() = KeepItemImage("https://p.kindpng.com/picc/s/72-722801_bread-roll-png-roll-of-bread-png-transparent.png")

    private val keepItems: MutableList<KeepItemImage> = items

    private lateinit var adapter: KeepItemRecyclerAdapter
    private lateinit var layoutManager: StaggeredGridLayoutManager
    private val repository = CountryRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager =
            StaggeredGridLayoutManager(
                resources.getInteger(R.integer.span_count), StaggeredGridLayoutManager.VERTICAL
            ).apply {
                gapStrategy = GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        mainItems.layoutManager = layoutManager
        adapter = KeepItemRecyclerAdapter(keepItems)
        mainItems.adapter = adapter

        mainButtonAddImage.setOnClickListener { addImageItem() }
    }

    private fun addImageItem() {
        repository.getImage().observe(this, Observer {
            val item = KeepItemImage(it.url)
            addKeepItem(item)
        })

    }

    private fun addKeepItem(item: KeepItemImage) {
        keepItems.add(0, item)
        adapter.notifyItemInserted(0)
        mainItems.smoothScrollToPosition(0)
    }
}