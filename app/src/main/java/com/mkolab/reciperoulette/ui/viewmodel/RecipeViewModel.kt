package com.mkolab.reciperoulette.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mkolab.reciperoulette.data.model.adapter.getAPIRecipe
import com.mkolab.reciperoulette.data.model.dataClass.Recipe
import com.mkolab.reciperoulette.data.model.dataClass.SearchCriteria
import com.mkolab.reciperoulette.data.util.FilterItem
import com.mkolab.reciperoulette.data.util.filterdata
import com.mkolab.reciperoulette.data.util.getProperty
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference


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

    val filterData = MutableLiveData(filterdata())


    private val _recipe = MutableLiveData<Recipe?>()
    val recipe: LiveData<Recipe?> get() = _recipe   // Local version of the recipe from the API call

    // 0 = not loaded/error, 1 = loading, 2 = loaded

    private val _navigateToDetails = MutableLiveData<Int>()
    val navigateToDetails: LiveData<Int> get() = _navigateToDetails


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
        var isRetryAttempted = false

        val apiKey = getProperty("key", context).toString()


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
                if (fetchedRecipe != null) {
                    isRetryAttempted = false
                    _navigateToDetails.value = 2
                } else {
                    _navigateToDetails.value = 0
                    Toast.makeText(getContext(), "No recipe found. Try changing your filters", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                if (!isRetryAttempted) {
                    isRetryAttempted = true
                    fetchRecipe(mealType)
                } else {
                    _navigateToDetails.value = 0
                    Log.e("RecipeViewModel", "Error fetching recipe: ${e.message}")
                    Toast.makeText(getContext(), "Error fetching recipe", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Function to reset all filters and update the UI
    fun resetFilters() {
        val originalFilterData = filterData.value
        originalFilterData?.cuisines?.forEach { it.isSelected = false }
        originalFilterData?.intolerances?.forEach { it.isSelected = false }
        originalFilterData?.diets?.forEach { it.isSelected = false }

        // Notify the UI of the updated filter data
        filterData.value = originalFilterData
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
        _navigateToDetails.value = 1
    }
}
