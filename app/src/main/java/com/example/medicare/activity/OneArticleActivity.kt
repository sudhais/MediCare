package com.example.medicare.activity


import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.medicare.R

class OneArticleActivity : AppCompatActivity() {

    var id=0
    private lateinit var txNameAt: TextView
    private lateinit var txBrandAt: TextView
    private lateinit var txCategoryAt: TextView
    private lateinit var txDateAt: TextView
    private lateinit var txDescriptionAt: TextView
    private lateinit var txImageA: ImageView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_article)


        toolbar = findViewById(R.id.toolbarV)
        setSupportActionBar(toolbar)

        txNameAt = findViewById(R.id.txNameAt)
        txBrandAt = findViewById(R.id.txBrandAt)
        txCategoryAt = findViewById(R.id.txCategoryAt)
        txDateAt = findViewById(R.id.txDateAt)
        txDescriptionAt = findViewById(R.id.txDescriptionAt)
        txImageA = findViewById(R.id.txImageA)

        try {
            var bundle: Bundle? = intent.extras
            id = bundle!!.getInt("MainActId", 0)
            if (id !=0){
                txNameAt.setText(bundle.getString("MainActTitle"))
                txBrandAt.setText(bundle.getString("MainActAuthor"))
                txCategoryAt.setText(bundle.getString("MainActCategory"))
                txDateAt.setText(bundle.getString("MainActDate"))
                txDescriptionAt.setText(bundle.getString("MainActContent"))
                var x= bundle.getByteArray("MainActImage")
                val bmp = BitmapFactory.decodeByteArray(x,0,x!!.size)
                txImageA.setImageBitmap(bmp)
            }
        }catch (ex: Exception){
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }

    public fun insertImg(view: View?){
        var myFileIntent = Intent(Intent.ACTION_GET_CONTENT)
        myFileIntent.setType("image/*")
        startActivityForResult(myFileIntent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            txImageA.setImageURI(data?.data)
        }
    }
}
