package com.example.examen_practice.Presentation.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examen_practice.Presentation.Models.Formulary

@Database(entities = [Formulary::class], version = 1, exportSchema = false)
abstract class FormularyDb: RoomDatabase() {
    abstract fun formularyDbDao(): FormularyDbDao

}