package com.airbender.mealsapp.model

import com.airbender.mealsapp.model.response.MealsCategories
import com.airbender.mealsapp.model.response.MealsResponse

class MealsRepository(private val webService: MealsWebService = MealsWebService()) {
    private var cachedMeals = listOf<MealsResponse>()
    suspend fun getMeals(): MealsCategories {
        val meals = webService.getMeals()
        cachedMeals = meals.categories
        return meals
    }

    fun getMeal(id: String): MealsResponse? {
        return cachedMeals.firstOrNull { it.id == id }
    }

    companion object {
        @Volatile
        private var instance: MealsRepository? = null

        fun getInstance() = instance ?: synchronized(this){
            instance ?: MealsRepository().also { instance = it }
        }
    }
}