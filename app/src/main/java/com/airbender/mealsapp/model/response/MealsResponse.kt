package com.airbender.mealsapp.model.response

data class MealsCategories(val categories: List<MealsResponse>)
data class MealsResponse(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)

class MealsRepository {

}