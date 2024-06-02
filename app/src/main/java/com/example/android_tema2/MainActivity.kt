package com.example.android_tema2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var animalAdapter: AnimalAdapter
    private val animalList = mutableListOf<Animal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animalNameEditText: EditText = findViewById(R.id.animalNameEditText)
        val continentEditText: EditText = findViewById(R.id.continentEditText)
        val addButton: Button = findViewById(R.id.addButton)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        animalAdapter = AnimalAdapter(animalList) { animal ->
            animalList.remove(animal)
            animalAdapter.notifyDataSetChanged()
        }

        recyclerView.adapter = animalAdapter

        addButton.setOnClickListener {
            val animalName = animalNameEditText.text.toString()
            val continent = continentEditText.text.toString()
            if (animalName.isNotEmpty() && continent.isNotEmpty()) {
                val animal = Animal(animalName, continent)
                animalList.add(animal)
                animalAdapter.notifyDataSetChanged()
                animalNameEditText.text.clear()
                continentEditText.text.clear()
            }
        }
    }
}
