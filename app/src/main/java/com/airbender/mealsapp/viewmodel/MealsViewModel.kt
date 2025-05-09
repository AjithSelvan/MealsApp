package com.airbender.mealsapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbender.mealsapp.model.MealsRepository
import com.airbender.mealsapp.model.response.MealsResponse
import kotlinx.coroutines.launch


class MealsViewModel(private val repository: MealsRepository = MealsRepository()) : ViewModel() {
    val meals: MutableState<List<MealsResponse>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch {
            meals.value = getMeals()
        }
    }

    private suspend fun getMeals(): List<MealsResponse> {
        return repository.getMeals().categories
    }
}