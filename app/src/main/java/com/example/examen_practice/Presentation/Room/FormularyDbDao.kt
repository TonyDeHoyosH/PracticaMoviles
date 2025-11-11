package com.example.examen_practice.Presentation.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examen_practice.Presentation.Models.Formulary
import kotlinx.coroutines.flow.Flow

@Dao  //data access observer
interface FormularyDbDao {
    @Query("SELECT * FROM formulary")
    fun getAll(): Flow<List<Formulary>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(formulary: Formulary)
}