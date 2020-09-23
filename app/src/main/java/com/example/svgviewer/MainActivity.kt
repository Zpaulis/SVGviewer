package com.example.svgviewer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.caverock.androidsvg.SVG
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL


class MainActivity : AppCompatActivity() {
    data class KeepItemImage(val uri: String)
// Sample SVG string
    val myItemFlagStr = SVG.getFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
            "<svg height=\"850\" width=\"1300\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" id=\"Flag of Ã…land\">\n" +
            "<rect fill=\"#0053a5\" height=\"850\" width=\"1300\" />\n" +
            "<g fill=\"#ffce00\">\n" +
            " <rect height=\"850\" width=\"250\" x=\"400\" />\n" +
            " <rect height=\"250\" width=\"1300\" y=\"300\" />\n" +
            "</g>\n" +
            "<g fill=\"#d21034\">\n" +
            " <rect height=\"850\" width=\"100\" x=\"475\" />\n" +
            " <rect height=\"100\" width=\"1300\" y=\"375\" />\n" +
            "</g>\n" +
            "</svg>")
    val bitmap3 = Bitmap.createBitmap(1235,650, Bitmap.Config.ARGB_8888)
    val canvas3 = Canvas(bitmap3)

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

        canvas3.drawARGB(0,255, 255, 255)
        myItemFlagStr.renderToCanvas(canvas3)

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
// test to read SVG as string from URL and render to Bitmap
        val queue = Volley.newRequestQueue(this)
        val myItemFlagStrRequest = StringRequest(
            Request.Method.GET, "https://restcountries.eu/data/bwa.svg",
            Response.Listener<String> { response ->
                val myItemFlagSvg = SVG.getFromString(response.toString())
                val bitmap2 = Bitmap.createBitmap(1600,800, Bitmap.Config.ARGB_8888)
                val canvas2 = Canvas(bitmap2)
                canvas2.drawARGB(0,255, 255, 255)
                myItemFlagSvg.renderToCanvas(canvas2)
                mainResImage.setImageDrawable(BitmapDrawable(resources, bitmap2))
                Log.d("see again", "meh")
            }, Response.ErrorListener {
                // didn't work
            });
        queue.add(myItemFlagStrRequest)
//        val myItemFlagStr = URL("https://restcountries.eu/data/bwa.svg").readText()  ---> NOT working

        repository.getImage().observe(this, Observer{
            var item = KeepItemImage(it.url)
//            val item = bitmap2 as KeepItemImage
            addKeepItem(item)
        })

    }

    private fun addKeepItem(item: KeepItemImage) {
        keepItems.add(0, item)
        adapter.notifyItemInserted(0)
        mainItems.smoothScrollToPosition(0)
    }
}