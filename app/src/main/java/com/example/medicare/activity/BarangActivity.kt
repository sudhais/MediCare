package com.example.medicare.activity

//import android.support.v7.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.medicare.Adapters.DBAdapter
import com.example.medicare.R
import java.io.ByteArrayOutputStream

class BarangActivity : AppCompatActivity() {

    var id=0
    private lateinit var txName: TextView
    private lateinit var txBrand: TextView
    private lateinit var txCategory: TextView
    private lateinit var txDate: TextView
    private lateinit var txDescription: TextView
    private lateinit var txImage: ImageView
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barang)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

         txName = findViewById(R.id.txName)
         txBrand = findViewById(R.id.txBrand)
         txCategory = findViewById(R.id.txCategory)
         txDate = findViewById(R.id.txDate)
         txDescription = findViewById(R.id.txDescription)
         txImage = findViewById(R.id.txImage)

        try {
            var bundle: Bundle? = intent.extras
            id = bundle!!.getInt("MainActId", 0)
            if (id !=0){
                txName.text = bundle.getString("MainActTitle")
                txBrand.text = bundle.getString("MainActAuthor")
                txCategory.text = (bundle.getString("MainActCategory"))
                txDate.text = (bundle.getString("MainActDate"))
                txDescription.text = (bundle.getString("MainActContent"))
                var x= bundle.getByteArray("MainActImage")
                val bmp = BitmapFactory.decodeByteArray(x,0,x!!.size)
                txImage.setImageBitmap(bmp)
            }
        }catch (ex: Exception){
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)

        val itemDelete: MenuItem = menu.findItem(R.id.action_delete)

        if (id ==0){
            itemDelete.isVisible = false
        }else{
            itemDelete.isVisible = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_save -> {
                var dbAdapter = DBAdapter(this)

                var txImage =  findViewById(R.id.txImage) as ImageView

                var values = ContentValues()
                values.put("Title", txName.text.toString())
                values.put("Author", txBrand.text.toString())
                values.put("Category", txCategory.text.toString())
                values.put("Date", txDate.text.toString())
                values.put("Content", txDescription.text.toString())

                val bitmap = (txImage.getDrawable() as BitmapDrawable).getBitmap()
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                val imageInByte = stream.toByteArray()
                values.put("Image", imageInByte)

                if (id == 0){
                    val mID = dbAdapter.insert(values)

                    if (mID > 0){
                        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    var selectionArgs = arrayOf(id.toString())
                    val mID = dbAdapter.update(values, "Id=?", selectionArgs)
                    if (mID > 0){
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            R.id.action_delete ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete Data")
                builder.setMessage("This Data Will Be Deleted")

                builder.setPositiveButton("Delete") {dialog: DialogInterface?, which: Int ->
                    var dbAdapter = DBAdapter(this)
                    val selectionArgs = arrayOf(id.toString())
                    dbAdapter.delete("Id=?", selectionArgs)
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                builder.setNegativeButton("Cancel"){dialog: DialogInterface?, which: Int ->  }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    public fun insertImg(view: View?){
        var myFileIntent = Intent(Intent.ACTION_GET_CONTENT)
        myFileIntent.setType("image/*")
        startActivityForResult(myFileIntent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            txImage.setImageURI(data?.data)
        }
    }
}