package com.example.medicare.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.database.FirebaseHelper
import com.example.medicare.models.CartModel
import com.example.medicare.models.MedicineModel

class CartAdapter(private val cartList: MutableList<CartModel>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var firebaseHelper = FirebaseHelper()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.txt_medname)
        var unitPrice: TextView = itemView.findViewById(R.id.txt_unit)
        var totalPrice: TextView = itemView.findViewById(R.id.txt_total)
        var qty: TextView = itemView.findViewById(R.id.txt_qty)
        var btn_delete: ImageView = itemView.findViewById(R.id.btn_delete)
        var img_view: ImageView = itemView.findViewById(R.id.img_view)
        var btn_plus:ImageView = itemView.findViewById(R.id.btn_plus)
        var btn_minus:ImageView = itemView.findViewById(R.id.btn_minus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  current = cartList[position]
        holder.name.text = current.name
        holder.unitPrice.text = "Rs.${current.unitPrice} per 1 med"
        holder.totalPrice.text = "Total Rs.${current.totalPrice}"
        holder.qty.text = current.qty.toString()

        val bytes = android.util.Base64.decode(current.image,android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        holder.img_view.setImageBitmap(bitmap)

        var medicineModel = MedicineModel()

        firebaseHelper.getSingleMedicine(current.medID!!){medicine ->
            medicineModel = medicine!!
        }

        holder.btn_plus.setOnClickListener {
            var quan = current.qty!!

            if(quan < medicineModel.stock!!) {
                quan++
                val value = "%.3f".format(current.unitPrice!! * quan)
                val newCart = CartModel(
                    current.medID,
                    current.userID,
                    current.name,
                    current.unitPrice,
                    value.toDouble(),
                    quan,
                    current.image
                )
                firebaseHelper.updateUserCart(newCart)
            }else{
                Toast.makeText(holder.itemView.context, "Out of stock amount", Toast.LENGTH_LONG ).show()
            }
        }

        holder.btn_minus.setOnClickListener {
            var quan = current.qty!!
            if(quan > 1){
                quan--
                val value = "%.3f".format(current.unitPrice!! * quan)
                val newCart = CartModel(
                    current.medID,
                    current.userID,
                    current.name,
                    current.unitPrice,
                    value.toDouble(),
                    quan,
                    current.image
                )
                firebaseHelper.updateUserCart(newCart)

            }

        }

        holder.btn_delete.setOnClickListener {
            holder.btn_delete.setImageResource(R.drawable.selected_delete)

            val context = holder.itemView.context
            firebaseHelper.deleteUserCart(current.medID!!, {
                // Task creation was successful
                Toast.makeText(context, "cart deleted successfully", Toast.LENGTH_SHORT).show()

            }, { exception ->
                // Task creation failed, handle the error
                Toast.makeText(context, "cart delete failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
        }


    }
}