package com.example.medicare.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.activity.MessageDetailActivity
import com.example.medicare.models.MessageModel

class MessageAdapter(private val originalMessageList: List<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(), Filterable {

    private var onItemClickListener: OnItemClickListener? = null
    private var messageModel: List<MessageModel> = ArrayList()
    private var messageList = originalMessageList
    private var filteredMessageList = originalMessageList

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSubject: TextView = itemView.findViewById(R.id.textViewSubject) // Replace with your view IDs
        val textViewContent: TextView = itemView.findViewById(R.id.textViewContent) // Replace with your view IDs
        val buttonEdit: Button = itemView.findViewById(R.id.buttonMessageEdit)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonMessageDelete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false) // Replace with your item layout
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = filteredMessageList[position]

        holder.textViewSubject.text = currentMessage.messageEmail
        holder.textViewContent.text = currentMessage.subject

        holder.buttonEdit.setOnClickListener {
            onItemClickListener?.onEditClick(position,currentMessage)
        }

        holder.buttonDelete.setOnClickListener {
            onItemClickListener?.onDeleteClick(position,currentMessage)
        }
    }

    override fun getItemCount(): Int {
        return filteredMessageList.size
    }

    interface OnItemClickListener {
        fun onEditClick(position: Int, messageModel: MessageModel)
        fun onDeleteClick(position: Int, messageModel: MessageModel)
    }

    // Search functionality
    private var searchQuery = ""

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchQuery = constraint?.toString() ?: ""
                val results = FilterResults()
                if (searchQuery.isEmpty()) {
                    results.values = originalMessageList
                } else {
                    val filteredList = originalMessageList.filter { message ->
                        message.subject!!.contains(searchQuery, ignoreCase = true) ||
                                message.messageEmail!!.contains(searchQuery, ignoreCase = true)
                    }
                    results.values = filteredList
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredMessageList = results?.values as List<MessageModel>
                notifyDataSetChanged()
            }
        }
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
    fun submitList(newList: List<MessageModel>) {
        messageModel = newList
        notifyDataSetChanged()
    }
}
