package com.airbender.mealsapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.airbender.mealsapp.model.MealsRepository
import com.airbender.mealsapp.model.response.MealsResponse

class MealsDetailsViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val repository : MealsRepository = MealsRepository.getInstance()

    var mealState = mutableStateOf<MealsResponse?>(null)

    init {
        val mealId = savedStateHandle.get<String>("mealId") ?: ""
        mealState.value = repository.getMeal(mealId)
    }
}