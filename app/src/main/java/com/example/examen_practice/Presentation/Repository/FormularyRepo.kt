package com.example.examen_practice.Presentation.Repository

import com.example.examen_practice.Presentation.Models.Formulary
import com.example.examen_practice.Presentation.Room.FormularyDbDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FormularyRepo @Inject constructor(
    private val formularyDbDao: FormularyDbDao
) {
    suspend fun insertFormulary(formulary: Formulary) = formularyDbDao.insert(formulary)
    fun getAllFormulary(): Flow<List<Formulary>> = formularyDbDao.getAll().flowOn(Dispatchers.IO).conflate()
}

