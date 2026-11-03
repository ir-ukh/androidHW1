package com.example.androidhw1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var items = mutableListOf<Int>()
    private var deletedStack = mutableListOf<Int>()
    private var nextNumber = 0

    fun handleAddClick(){
        if(deletedStack.isNotEmpty()){
            val restored = deletedStack.removeAt(deletedStack.size - 1)
            items.add(restored)
            notifyItemInserted(items.size - 1)
        } else {
            items.add(nextNumber)
            notifyItemInserted(items.size -1)
            nextNumber++
        }
    }

    fun removeItemAt(position: Int){
        val removedNum = items.removeAt(position)
        deletedStack.add(removedNum)
        notifyItemRemoved(position)
    }

    fun getItemsList(): ArrayList<Int> = ArrayList(items)
    fun getDeletedStack(): ArrayList<Int> = ArrayList(deletedStack)
    fun getNextNumber(): Int = nextNumber
    fun getCount(): Int = items.size

    fun restoreState(savedItems: ArrayList<Int>, savedStack: ArrayList<Int>, savedNextNumber: Int) {
        items.addAll(savedItems)
        deletedStack.addAll(savedStack)
        nextNumber = savedNextNumber
        notifyDataSetChanged()
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
        val colorRes = if (number % 2 == 0) R.color.card_red else R.color.card_blue
        holder.text.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, colorRes))

        holder.itemView.setOnClickListener{
            removeItemAt(holder.adapterPosition)
        }

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