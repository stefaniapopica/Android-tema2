package com.example.android_tema2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animals")
    fun getAll(): List<Animal>

    @Query("SELECT * FROM animals WHERE LOWER(name) = LOWER(:name) LIMIT 1")
    fun getAnimalByName(name: String): Animal?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(animal: Animal)

    @Update
    fun update(animal: Animal)

    @Delete
    fun delete(animal: Animal)
}
