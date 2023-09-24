package com.example.reciperoulette.data.util

data class FilterItem(val name: String, var isSelected: Boolean)


fun filterdata(): FilterData {
    val cuisines = listOf(
        FilterItem("African", false),
        FilterItem("Asian", false),
        FilterItem("American", false),
        FilterItem("British", false),
        FilterItem("Cajun", false),
        FilterItem("Caribbean", false),
        FilterItem("Chinese", false),
        FilterItem("Eastern European", false),
        FilterItem("European", false),
        FilterItem("French", false),
        FilterItem("German", false),
        FilterItem("Greek", false),
        FilterItem("Indian", false),
        FilterItem("Irish", false),
        FilterItem("Italian", false),
        FilterItem("Japanese", false),
        FilterItem("Jewish", false),
        FilterItem("Korean", false),
        FilterItem("Latin American", false),
        FilterItem("Mediterranean", false),
        FilterItem("Mexican", false),
        FilterItem("Middle Eastern", false),
        FilterItem("Nordic", false),
        FilterItem("Southern", false),
        FilterItem("Spanish", false),
        FilterItem("Thai", false),
        FilterItem("Vietnamese", false)
    )

    val diets = listOf(
        FilterItem("Gluten Free", false),
        FilterItem("Ketogenic", false),
        FilterItem("Vegetarian", false),
        FilterItem("Lacto-\nVegetarian", false),
        FilterItem("Ovo-\nVegetarian", false),
        FilterItem("Vegan", false),
        FilterItem("Pescetarian", false),
        FilterItem("Paleo", false),
        FilterItem("Primal", false),
        FilterItem("Low FODMAP", false),
        FilterItem("Whole30", false),
    )

    val intolerances = listOf(
        FilterItem("Dairy", false),
        FilterItem("Egg", false),
        FilterItem("Gluten", false),
        FilterItem("Grain", false),
        FilterItem("Peanut", false),
        FilterItem("Seafood", false),
        FilterItem("Sesame", false),
        FilterItem("Shellfish", false),
        FilterItem("Soy", false),
        FilterItem("Sulfite", false),
        FilterItem("Tree Nut", false),
        FilterItem("Wheat", false),
    )

    return FilterData(cuisines, intolerances, diets)
}

class FilterData(val cuisines: List<FilterItem>, val intolerances: List<FilterItem>, val diets: List<FilterItem>) {
    val selectedCuisines: List<FilterItem>
        get() = cuisines.filter { it.isSelected }

    val selectedIntolerances: List<FilterItem>
        get() = intolerances.filter { it.isSelected }

    val selectedDiets: List<FilterItem>
        get() = diets.filter { it.isSelected }
}