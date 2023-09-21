package com.example.reciperoulette.ui.viewmodel
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.reciperoulette.data.model.adapter.getAPIRecipe
import com.example.reciperoulette.data.model.dataClass.Recipe
import com.example.reciperoulette.data.model.dataClass.SearchCriteria
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.Locale

class SharedViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class RecipeViewModel(context: Context) : ViewModel() {
    private val contextRef: WeakReference<Context> = WeakReference(context)

    private fun getContext(): Context? {
        return contextRef.get()
    }

    val vegetarianSwitch = mutableStateOf(false)

    private val _recipe = MutableLiveData<Recipe?>()
    val recipe: LiveData<Recipe?> get() = _recipe   // Local version of the recipe from the API call

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean> get() = _navigateToDetails

    val selectedCuisine = MutableLiveData<String>("")
    val selectedDiet = MutableLiveData<String>("")
    val selectedMealType = MutableLiveData<String>("")
    val selectedMaxReadyTime = MutableLiveData<Int>(999)

    private val _currentCriteria = MutableLiveData<SearchCriteria>()
    val currentCriteria: LiveData<SearchCriteria> get() = _currentCriteria
    init {
        applyFilters()
        //loadRecipeData()
    }
    fun fetchRecipe(mealType: String) {
        viewModelScope.launch {
            // Get the base criteria from LiveData or return if it's null
            val baseCriteria = _currentCriteria.value ?: return@launch

            // Add the mealtype to the criteria
            val additionalCriteria = SearchCriteria(type = mealType.lowercase())

            // Merge the base criteria with the additional criteria
            val finalCriteria = baseCriteria.mergeWith(additionalCriteria)
            /*  val criteria = SearchCriteria(
                  cuisine = listOf(selectedCuisine.value ?: ""),
                  diet = listOf(selectedDiet.value ?: "vegetarian"),
                  type = selectedMealType.value ?: "",
                  maxReadyTime = selectedMaxReadyTime.value
              )
            val tags = recipeStringMaker(mealType)
            Log.d("string", tags)*/

            val fetchedRecipe = getAPIRecipe(finalCriteria)
            _recipe.value = fetchedRecipe   // Set the recipe to the fetched recipe
            Log.d("RecipeViewModel", "Recipe fetched: ${fetchedRecipe?.title}")
            if (fetchedRecipe != null)
                _navigateToDetails.value = true
            else
                Toast.makeText(getContext(), "No recipe found", Toast.LENGTH_SHORT).show()
        }
    }

    fun applyFilters() {    // Creates a new SearchCriteria object from the selected filters
        _currentCriteria.value = SearchCriteria(
            cuisine = listOfNotNull(selectedCuisine.value),
            diet = listOfNotNull(selectedDiet.value),
            type = selectedMealType.value,
            maxReadyTime = selectedMaxReadyTime.value
        )
    }

    // Creates string of tags for API call
    private fun recipeStringMaker(mealType: String): String{
        return if (vegetarianSwitch.value) {
            "vegetarian,$mealType"
        } else {
            mealType
        }
    }

    fun toggleVegetarianSwitch() {
        vegetarianSwitch.value = !vegetarianSwitch.value
        Log.d("switch", vegetarianSwitch.value.toString())
    }

    fun onNavigationDone() {
        _navigateToDetails.value = false
    }
}
