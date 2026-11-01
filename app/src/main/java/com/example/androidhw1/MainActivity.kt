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
    private val keyCount = "key_count"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ItemAdapter()

        val columns = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 3
        recyclerView.layoutManager = GridLayoutManager(this, columns)
        recyclerView.adapter = adapter

        val savedCount = savedInstanceState?.getInt(keyCount) ?: 0
        if (savedCount > 0) {
            adapter.restoreItems(savedCount)
        }

        val addButton = findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener {
            adapter.addItem()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(keyCount, adapter.getCount())
    }
}
