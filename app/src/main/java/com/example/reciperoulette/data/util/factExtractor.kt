package com.example.reciperoulette.data.util

import androidx.compose.ui.res.integerArrayResource
import com.example.reciperoulette.data.model.dataClass.Instruction
import com.example.reciperoulette.data.model.dataClass.RecipeA
import com.example.reciperoulette.data.model.dataClass.Step

fun factExtractor(recipe: RecipeA): RecipeA {
    fun extractValue(pattern: String, input: String): Int? {
        val regex = pattern.toRegex()
        return regex.find(input)?.groups?.get(1)?.value?.toIntOrNull()
    }

    return try {
        val calories = extractValue("""<b>(\d+) calories</b>""", recipe.summary)
        val fat = extractValue("""<b>(\d+)g of fat</b>""", recipe.summary)
        val protein = extractValue("""<b>(\d+)g of protein</b>""", recipe.summary)

        val formattedIngredients = recipe.extendedIngredients.map { ingredient ->
            ingredient.copy(amount = formatAmount(ingredient.amount.toDouble()))
        }

        val newInstructions = recipe.analyzedInstructions.map { instruction ->
            Instruction(steps = instruction.steps.map { step ->
                Step(number = step.number, step = instructionFormatFix(step.step))
            })
        }

        //println("Calories: $calories, Fat: $fat, Protein: $protein")

        recipe.copy(
            calories = calories ?: recipe.calories,
            fat = fat ?: recipe.fat,
            protein = protein ?: recipe.protein,
            extendedIngredients = formattedIngredients,
            analyzedInstructions = newInstructions
        )
    } catch (e: Exception) {
        recipe
    }
}

fun instructionFormatFix(input: String): String {
    return input.replace("\\.(?!\\s)".toRegex(), ". ")}

fun formatAmount(amount: Double): String {
    return if (amount % 1 == 0.0) {
        amount.toInt().toString()
    } else {
        amount.toString()
    }
}

