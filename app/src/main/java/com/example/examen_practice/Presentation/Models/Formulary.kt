package com.example.examen_practice.Presentation.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formulary")
data class Formulary(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo( name = "name")
    val name: String,
    @ColumnInfo( name = "email")
    val email: String,
    @ColumnInfo( name = "age")
    val age: String
)
