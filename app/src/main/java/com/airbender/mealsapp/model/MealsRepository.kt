package com.airbender.mealsapp.model

import com.airbender.mealsapp.model.response.MealsCategories

class MealsRepository(private val webService: MealsWebService = MealsWebService()) {
    suspend fun getMeals(): MealsCategories {
        return webService.getMeals()
    }
}