package com.example.medicare.activity


//import android.support.v7.app.AppCompatActivity;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton

//import kotlinx.android.synthetic.main.activity_main_article_admin.*
//import kotlinx.android.synthetic.main.content_main.*

class MainArticleAdminActivity : AppCompatActivity() {

    private var listBarang = ArrayList<Barang>()
    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton
    private lateinit var lvBarang: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_article_admin)

        fab = findViewById(R.id.fab)
        toolbar = findViewById(R.id.toolbar)
        lvBarang = findViewById(R.id.lvBarang)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var intent = Intent(this, BarangActivity::class.java)
            startActivity(intent)
        }

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
        lvBarang.adapter = barangAdapter
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
                view = layoutInflater.inflate(R.layout.barang, parent, false)
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

            val x = mBarang.image
            val bmp = BitmapFactory.decodeByteArray(x,0, x!!.size)

            vh.tvImage.setImageBitmap(bmp)

            lvBarang.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
                updateBarang(barangList[position])
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

    private fun updateBarang(barang: Barang) {
        var  intent = Intent(this, BarangActivity::class.java)
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
        val tvImage: ImageView

        init {
            this.tvName = view?.findViewById(R.id.tvName) as TextView
            this.tvBrand = view?.findViewById(R.id.tvBrand) as TextView
            this.tvPrice = view?.findViewById(R.id.tvPrice) as TextView
            this.tvImage = view?.findViewById(R.id.tvImage) as ImageView
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