package com.example.reciperoulette.data.model.dataClass

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class RecipeResponse(
    val recipes: List<RecipeA>
)

@JsonClass(generateAdapter = true)
data class RecipeA(
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val image: String,
    val summary: String,
    val calories: Int? = 0,
    val protein: Int? = 0,
    val fat: Int? = 0,
    val creditsText: String,
    @Json(name = "extendedIngredients") val extendedIngredients: List<IngredientA>,
    val analyzedInstructions: List<Instruction>
)

@JsonClass(generateAdapter = true)
data class IngredientA(
    @Json(name = "nameClean") val nameClean: String,
    val amount: String,
    val unit: String
)

@JsonClass(generateAdapter = true)
data class Instruction(
    val steps: List<Step>
)

@JsonClass(generateAdapter = true)
data class Step(
    val number: Int,
    val step: String
)