package com.example.reciperoulette.data.model.dataClass



data class RecipeList(
    val recipes: List<Recipe>
)

data class Recipe(
    val imageURL: String,
    val name: String,
    val cuisine: String,
    val total_time: String,
    val ingredients: List<Ingredient>,
    val instructions: String
)

data class Ingredient(
    val name: String,
    val amount: String
)