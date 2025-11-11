package com.example.examen_practice.Presentation.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen_practice.Presentation.Models.Formulary
import com.example.examen_practice.Presentation.Repository.FormularyRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FormularyViewModel @Inject constructor( private val formularyRepo: FormularyRepo): ViewModel() {
    private val _formularyList = MutableStateFlow<List<Formulary>>(emptyList())
    val formularyList = _formularyList.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            formularyRepo.getAllFormulary().collect{ item ->
                if(item.isNullOrEmpty()){
                    _formularyList.value = emptyList()
                }else{
                    _formularyList.value = item
                }
            }
        }
    }

    fun addformulary(formulary: Formulary) = viewModelScope.launch {
        formularyRepo.insertFormulary(formulary)
    }
}