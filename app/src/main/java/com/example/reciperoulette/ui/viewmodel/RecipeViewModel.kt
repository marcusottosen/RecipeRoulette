package com.example.reciperoulette.ui.viewmodel
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.reciperoulette.data.model.adapter.getAPIRecipe
import com.example.reciperoulette.data.model.dataClass.Recipe
import com.example.reciperoulette.data.model.dataClass.RecipeA
import com.example.reciperoulette.data.model.supabaseAdapter.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class RecipeViewModel(private val context: Context) : ViewModel() {
    // LiveData to hold the recipe data
    private val _recipes = MutableLiveData<List<Recipe>>()
    //val recipes: LiveData<List<Recipe>> = _recipes

    private val _selectedRecipe = MutableLiveData<Recipe?>()
    val selectedRecipe: LiveData<Recipe?> = _selectedRecipe

    private val _selectedMealType = MutableStateFlow<String?>(null)
    // Expose the selected meal type as a read-only state flow
    val selectedMealType = _selectedMealType.asStateFlow()

    init {
        // Load recipe data when the ViewModel is initialized
        loadRecipeData()
    }

    private fun loadRecipeData() {
        val repository = RecipeRepository()
        _recipes.value = repository.loadRecipeData(context, "data.json")
    }

    /*fun loadRecipeData(context: Context) {
        val fileName = "data.json"
        try {
            Log.d("json", "Loading data..")
            // Replace with your JSON file's name and path
            val jsonString = readJsonFromAssets(context, fileName)

            val gson = Gson()
            val recipeList = gson.fromJson(jsonString, RecipeList::class.java)
            _recipes.value = recipeList.recipes
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

    fun selectRandomRecipe() {
        val recipesList = _recipes.value ?: emptyList()
        if (recipesList.isNotEmpty()) {
            _selectedRecipe.value = recipesList.random()
        }
    }

    fun getRecipe(): Recipe? {
        return _selectedRecipe.value
    }

    fun setRecipe(recipe: Recipe?) {
        _selectedRecipe.value = recipe
    }

    // Function to set the selected meal type
    fun setSelectedMealType(mealType: String) {
        _selectedMealType.value = mealType
    }



    // ##################################




    private val _recipe = MutableLiveData<RecipeA?>()
    val recipe: LiveData<RecipeA?> get() = _recipe

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean> get() = _navigateToDetails

    fun fetchRecipe() {
        viewModelScope.launch {
            val fetchedRecipe = getAPIRecipe() // Adjust getAPIRecipe to return the RecipeA object
            _recipe.value = fetchedRecipe
            Log.d("RecipeViewModel", "Recipe fetched: ${fetchedRecipe?.title}")
            _navigateToDetails.value = true
        }
    }

    fun onNavigationDone() {
        _navigateToDetails.value = false
    }




}
