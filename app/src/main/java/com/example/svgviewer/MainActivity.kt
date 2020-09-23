package com.example.svgviewer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
import com.caverock.androidsvg.SVG
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    data class KeepItemImage(val uri: String)

    val items
        get() = MutableList(1) { imageItem }
    val imageItem get() = KeepItemImage("https://p.kindpng.com/picc/s/72-722801_bread-roll-png-roll-of-bread-png-transparent.png")
//    val imageItem get() = KeepItemImage("https://restcountries.eu/data/gbr.svg")


    private val keepItems: MutableList<KeepItemImage> = items

    private lateinit var adapter: KeepItemRecyclerAdapter
    private lateinit var layoutManager: StaggeredGridLayoutManager
    private val repository = CountryRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.test)

        if (svg.getDocumentWidth() !== -1F) {

            // set your custom height and width for the svg
//            svg.documentHeight = 400F
//            svg.documentWidth = 600F

            // create a canvas to draw onto
            val bitmap = Bitmap.createBitmap(1235,650, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            // canvas - white background
            canvas.drawARGB(0,255, 255, 255)

            // Render our document onto our canvas
            svg.renderToCanvas(canvas)

            // set the bitmap to imageView
            mainResImage.setImageDrawable(BitmapDrawable(resources, bitmap))
        }


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
        repository.getImage().observe(this, Observer{
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