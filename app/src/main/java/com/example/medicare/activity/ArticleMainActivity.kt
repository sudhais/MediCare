package com.example.medicare.activity


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.medicare.Adapters.DBAdapter
import com.example.medicare.R
import com.example.medicare.models.Barang


class ArticleMainActivity : AppCompatActivity() {

    private var listBarang = ArrayList<Barang>()
    private lateinit var lvBarangA: ListView
    private lateinit var toolbarA: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_main)
        lvBarangA = findViewById(R.id.lvBarangA)

        toolbarA = findViewById(R.id.toolbarA)
        setSupportActionBar(toolbarA)
        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    @SuppressLint("Range")
    private fun loadData() {
        var dbAdapter = DBAdapter(this)
        var cursor = dbAdapter.allQuery()

        listBarang.clear()
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val author = cursor.getString(cursor.getColumnIndex("Author"))
                val category = cursor.getString(cursor.getColumnIndex("Category"))
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                val content = cursor.getString(cursor.getColumnIndex("Content"))
                val image = cursor.getBlob(cursor.getColumnIndex("Image"))
                listBarang.add(Barang(id, title, author, category, content, date, image))
            }while (cursor.moveToNext())
        }

        var barangAdapter = BarangAdapter(this, listBarang)

        lvBarangA.adapter = barangAdapter
    }

    inner class BarangAdapter: BaseAdapter{

        private var barangList = ArrayList<Barang>()
        private var context: Context? = null

        constructor(context: Context, barangList: ArrayList<Barang>) : super(){
            this.barangList = barangList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view: View?
            val vh: ViewHolder

            if (convertView == null){
                view = layoutInflater.inflate(R.layout.activity_article, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
                Log.i("db", "set tag for ViewHolder, position: " + position)
            }else{
                view = convertView
                vh = view.tag as ViewHolder
            }

            var mBarang = barangList[position]

            if(mBarang.title!!.length > 20){
                vh.tvName.text = mBarang.title!!.subSequence(0, 20) as String + "..."
            }else{
                vh.tvName.text = mBarang.title!!
            }
            vh.tvBrand.text = mBarang.author
            vh.tvPrice.text = mBarang.date
            vh.tvCategory.text = mBarang.category

            val x = mBarang.image
            val bmp = BitmapFactory.decodeByteArray(x,0, x!!.size)

            vh.tvImage.setImageBitmap(bmp)

            lvBarangA.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
                selectBarang(barangList[position])
            }

            return view
        }

        override fun getItem(position: Int): Any {
            return barangList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return barangList.size
        }

    }

    private fun selectBarang(barang: Barang) {
        var  intent = Intent(this, OneArticleActivity::class.java)
        intent.putExtra("MainActId", barang.id)
        intent.putExtra("MainActTitle", barang.title)
        intent.putExtra("MainActAuthor", barang.author)
        intent.putExtra("MainActCategory", barang.category)
        intent.putExtra("MainActDate", barang.date)
        intent.putExtra("MainActContent", barang.content)
        intent.putExtra("MainActImage", barang.image)
        startActivity(intent)
    }

    private class ViewHolder(view: View?){
        val tvName: TextView
        val tvBrand: TextView
        val tvPrice: TextView
        val tvCategory: TextView
        val tvImage: ImageView

        init {
            this.tvName = view?.findViewById(R.id.tvNameA) as TextView
            this.tvBrand = view?.findViewById(R.id.tvBrandA) as TextView
            this.tvPrice = view?.findViewById(R.id.tvPriceA) as TextView
            this.tvCategory = view?.findViewById(R.id.tvCategoryA) as TextView
            this.tvImage = view?.findViewById(R.id.tvImageA) as ImageView
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
