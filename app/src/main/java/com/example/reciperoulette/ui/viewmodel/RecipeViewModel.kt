package com.example.reciperoulette.ui.viewmodel
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.reciperoulette.data.model.adapter.getAPIRecipe
import com.example.reciperoulette.data.model.dataClass.Recipe
import com.example.reciperoulette.data.model.dataClass.SearchCriteria
import kotlinx.coroutines.launch

class SharedViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class RecipeViewModel : ViewModel() {
    // LiveData to hold the recipe data
    //private val _recipes = MutableLiveData<List<Recipe>>()
    //val recipes: LiveData<List<Recipe>> = _recipes

    //private val _selectedRecipe = MutableLiveData<Recipe?>()
    //val selectedRecipe: LiveData<Recipe?> = _selectedRecipe

    //private val _selectedMealType = MutableStateFlow<String?>(null)
    // Expose the selected meal type as a read-only state flow
    //val selectedMealType = _selectedMealType.asStateFlow()


/*
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

*/

    // ##################################


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
        // Load recipe data when the ViewModel is initialized
        //loadRecipeData()
    }
    fun fetchRecipe(mealType: String) {
        viewModelScope.launch {
            val baseCriteria = _currentCriteria.value ?: return@launch
            val additionalCriteria = SearchCriteria(type = mealType)
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
            _recipe.value = fetchedRecipe
            Log.d("RecipeViewModel", "Recipe fetched: ${fetchedRecipe?.title}")
            _navigateToDetails.value = true
        }
    }

    fun applyFilters() {
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
