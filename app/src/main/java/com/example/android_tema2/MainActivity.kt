package com.example.android_tema2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var animalAdapter: AnimalAdapter
    private val animalList = mutableListOf<Animal>()
    private val validContinents = listOf("Africa", "Asia", "Europe", "North America", "South America", "Australia", "Antarctica")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animalNameEditText: EditText = findViewById(R.id.animalNameEditText)
        val continentEditText: EditText = findViewById(R.id.continentEditText)
        val addButton: Button = findViewById(R.id.addButton)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = AnimalDatabase.getDatabase(this)
        val animalDao = db.animalDao()

        animalAdapter = AnimalAdapter(animalList) { animal ->
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    animalDao.delete(animal)
                }
                animalList.remove(animal)
                animalAdapter.notifyDataSetChanged()
            }
        }

        recyclerView.adapter = animalAdapter

        addButton.setOnClickListener {
            val animalName = animalNameEditText.text.toString()
            val continent = continentEditText.text.toString()

            if (animalName.isEmpty() || continent.isEmpty()) {
                Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!validContinents.contains(continent)) {
                Toast.makeText(this, "Invalid continent", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val existingAnimal = animalDao.getAnimalByName(animalName)
                    if (existingAnimal != null) {
                        val updatedAnimal = existingAnimal.copy(continent = continent)
                        animalDao.update(updatedAnimal)
                    } else {
                        val newAnimal = Animal(name = animalName, continent = continent)
                        animalDao.insert(newAnimal)
                    }
                    animalList.clear()
                    animalList.addAll(animalDao.getAll())
                }
                animalAdapter.notifyDataSetChanged()
                animalNameEditText.text.clear()
                continentEditText.text.clear()
            }
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                animalList.addAll(animalDao.getAll())
            }
            animalAdapter.notifyDataSetChanged()
        }
    }
}
