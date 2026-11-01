package com.example.androidhw1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var items = listOf<Int>()
    fun addItem() {
        val newList = items.toMutableList()
        newList.add(newList.size)
        updateList(newList)
    }
    fun restoreItems(count: Int) {
        val newList = mutableListOf<Int>()
        for (i in 0 until count) {
            newList.add(i)
        }
        updateList(newList)
    }

    fun getCount(): Int = items.size

    private fun updateList(newList: List<Int>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size
            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newList[newItemPosition]
            }
        })

        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.cardText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val number = items[position]
        holder.text.text = number.toString()

        // чет крас нечет син
        val colorRes = if (position % 2 == 0) R.color.card_red else R.color.card_blue
        holder.text.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, colorRes))

        holder.itemView.post {
            val width = holder.itemView.width
            if (width > 0) {
                val params = holder.itemView.layoutParams
                params.height = width
                holder.itemView.layoutParams = params
            }
        }
    }

    override fun getItemCount(): Int = items.size
}