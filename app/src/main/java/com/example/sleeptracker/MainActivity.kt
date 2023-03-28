package com.example.sleeptracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sleeptracker.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val items = mutableListOf<Item>()
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)
        itemRecyclerView = findViewById(R.id.itemsRv)
        val itemAdapter = ItemAdapter(this, items)

        itemRecyclerView.adapter = itemAdapter
        lifecycleScope.launch(IO){
            (application as ItemApplication).db.itemDao().getAll().collect(){
                    databaseList ->databaseList.map {entity ->
                Item(
                    entity.id,
                    entity.day,
                    entity.duration,
                    entity.isNap
                )
            }.also { mappedList ->
                items.clear()
                items.addAll(mappedList)
                itemAdapter.notifyDataSetChanged()
            }
            }
        }


        itemRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            itemRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        itemAdapter.setOnItemLongClickListener(object : ItemAdapter.OnItemLongClickListener{
            override fun onItemLongClick(itemView: View?, position: Int) {
                val removeID = items[position].id
                lifecycleScope.launch(IO) {
                    (application as ItemApplication).db.itemDao().deleteEntity(removeID)
                }
                itemAdapter.notifyItemRemoved(position)
            }
        })

        val intent = Intent(this, DetailActivity::class.java)
        var button = findViewById<Button>(R.id.submit)
        button.setOnClickListener{
            startActivity(intent)
            finish()
        }
//        Toast.makeText(this, "id is $id", Toast.LENGTH_SHORT).show()
        var day = getIntent().getIntExtra("day", 0)
        var duration = getIntent().getDoubleExtra("duration", 0.0)
        var isNap = getIntent().getBooleanExtra("isNap", false)

//        Toast.makeText(this, "day $day, duration $duration hours, nap is $isNap", Toast.LENGTH_SHORT).show()
//            Log.d("check", "day $day, duration: $duration, nap is $isNap")
        if (duration != 0.0)
        {
            lifecycleScope.launch(IO) {
                (application as ItemApplication).db.itemDao().insert(Item(Random.nextLong(0, 100000), day, duration, isNap))
            }
        }

    }
}