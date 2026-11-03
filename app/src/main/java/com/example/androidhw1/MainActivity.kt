package com.example.androidhw1

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private val keyItems = "key_items"
    private val keyStack = "key_stack"
    private val keyNext = "key_next"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ItemAdapter()

        val columns = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 3
        recyclerView.layoutManager = GridLayoutManager(this, columns)
        recyclerView.adapter = adapter

        val savedItems = savedInstanceState?.getIntegerArrayList(keyItems)
        val savedStack = savedInstanceState?.getIntegerArrayList(keyStack)
        val savedNext = savedInstanceState?.getInt(keyNext) ?: 0
        if (savedItems != null && savedStack != null) {
            adapter.restoreState(savedItems, savedStack, savedNext)
        }
        val addButton = findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener {
            adapter.handleAddClick()}}// новый варик

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList(keyItems, adapter.getItemsList())
        outState.putIntegerArrayList(keyStack, adapter.getDeletedStack())
        outState.putInt(keyNext, adapter.getNextNumber())
    }
}
