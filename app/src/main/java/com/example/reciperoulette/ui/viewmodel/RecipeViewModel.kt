package com.example.reciperoulette.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.reciperoulette.data.model.adapter.getAPIRecipe
import com.example.reciperoulette.data.model.dataClass.Recipe
import com.example.reciperoulette.data.model.dataClass.SearchCriteria
import com.example.reciperoulette.data.util.FilterItem
import com.example.reciperoulette.data.util.getProperty
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.Properties


class SharedViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



class RecipeViewModel(context: Context) : ViewModel() {
    private val contextRef = WeakReference(context)
    fun getContext(): Context? {
        return contextRef.get()
    }

    //private val contextRef: WeakReference<Context> = WeakReference(context)

   // private fun getContext(): Context? {
   //     return contextRef.get()
   // }

    val vegetarianSwitch = mutableStateOf(false)

    private val _recipe = MutableLiveData<Recipe?>()
    val recipe: LiveData<Recipe?> get() = _recipe   // Local version of the recipe from the API call

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean> get() = _navigateToDetails


    private val _currentCriteria = MutableLiveData<SearchCriteria>()
    init {
        _currentCriteria.value = SearchCriteria(    // Make a searchCriteria with default values. For future, get from local storage
            cuisine = null,
            diet = null,
            intolerances = null,
            type = null,
            maxReadyTime = null
        )
    }


    fun fetchRecipe(mealType: String) {
        val context = contextRef.get()
        val resources = context!!.resources


        val apiKey = getProperty("key", context).toString()
        Log.d("KEY in VM", "myKey: $apiKey")






        viewModelScope.launch {
            try {
                // Get the base criteria from LiveData or return if it's null
                val baseCriteria = _currentCriteria.value ?: return@launch

                // Add the mealtype to the criteria
                val additionalCriteria = SearchCriteria(type = mealType.lowercase())

                // Merge the base criteria with the additional criteria
                val finalCriteria = baseCriteria.mergeWith(additionalCriteria)

                val fetchedRecipe = getAPIRecipe(finalCriteria, apiKey = apiKey!!)
                _recipe.value = fetchedRecipe   // Set the recipe to the fetched recipe
                Log.d("RecipeViewModel", "Recipe fetched: ${fetchedRecipe?.title}")
                if (fetchedRecipe != null)
                    _navigateToDetails.value = true
                else
                    Toast.makeText(getContext(), "No recipe found", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                // Handle exceptions here
                Log.e("RecipeViewModel", "Error fetching recipe: ${e.message}")
                Toast.makeText(getContext(), "Error fetching recipe", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun applyFilters(selectedCuisines: List<FilterItem>, selectedIntolerances: List<FilterItem>, selectedDiets: List<FilterItem>) {
        Log.d("ViewModel", selectedCuisines.toString())

        _currentCriteria.value = SearchCriteria(
            cuisine = selectedCuisines.map { it.name },
            diet = selectedDiets.map { it.name },
            intolerances = selectedIntolerances.map { it.name },
            type = null,
            maxReadyTime = null
        )
    }

    fun onNavigationDone() {
        _navigateToDetails.value = false
    }
}
