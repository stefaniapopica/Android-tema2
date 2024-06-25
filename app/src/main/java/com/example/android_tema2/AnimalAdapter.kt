
package com.example.android_tema2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimalAdapter(private val animalList: List<Animal>, private val onDeleteClickListener: (Animal) -> Unit) :
    RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animalNameTextView: TextView = itemView.findViewById(R.id.animalNameTextView)
        val continentTextView: TextView = itemView.findViewById(R.id.continentTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animalList[position]
        holder.animalNameTextView.text = animal.name
        holder.continentTextView.text = animal.continent
        holder.deleteButton.setOnClickListener {
            onDeleteClickListener(animal)
        }
    }

    override fun getItemCount() = animalList.size
}
