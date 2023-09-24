package com.example.reciperoulette.ui.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.reciperoulette.data.util.NavigationRoute
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel
/*
@Composable
fun FilterPage(navController: NavController, viewModel: RecipeViewModel){
    LaunchedEffect(Unit) {
        println("FilterPage recomposed!")
    }
    val selectedCuisine by viewModel.selectedCuisine.observeAsState(initial = null)
    val selectedDiet by viewModel.selectedDiet.observeAsState(initial = null)
    val selectedIntolerance by viewModel.selectedDiet.observeAsState(initial = null)
    val selectedType by viewModel.selectedDiet.observeAsState(initial = null)
    val maxReadyTime by viewModel.selectedDiet.observeAsState(initial = null)
    val selectedCuisines = remember { mutableStateOf<Set<String>>(setOf()) }
    val currentSelectedCuisines = rememberUpdatedState(selectedCuisines.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CuisineCheckboxes(selectedCuisines) { cuisine ->
            if (currentSelectedCuisines.value.contains(cuisine)) {
                selectedCuisines.value = selectedCuisines.value - cuisine
            } else {
                selectedCuisines.value = selectedCuisines.value + cuisine
            }
        }


       // CuisineGrid(selectedCuisines) { cuisine ->
       //     // Handle cuisine selection
       //     if (selectedCuisines.value.contains(cuisine)) {
       //         viewModel.selectedCuisine.value = "" // Reset or handle deselection
       //     } else {
       //         viewModel.selectedCuisine.value = cuisine
       //     }
       // }
        CuisineDropdown(viewModel.selectedCuisine) { cuisine ->
            // Handle cuisine selection
            viewModel.selectedCuisine.value = cuisine
        }

        DietRadioButton(viewModel.selectedDiet) { diet ->
            // Handle diet selection
            viewModel.selectedDiet.value = diet
        }

        // Add similar components for other filters

        Button(onClick = {
            //viewModel.applyFilters()
            navController.navigate(NavigationRoute.Homepage.route)
        }) {
            Text("Apply Filters")
        }
    }
}



@Composable
fun CuisineCheckboxes(selectedCuisines: MutableState<Set<String>>, onCuisineSelected: (String) -> Unit) {
    val cuisines = listOf(
        "African",
        "Asian",
        "American",
        "British",
        "Cajun",
        "Caribbean",
        "Chinese",
        "Eastern European",
        "European",
        "French",
        "German",
        "Greek",
        "Indian",
        "Irish",
        "Italian",
        "Japanese",
        "Jewish",
        "Korean",
        "Latin American",
        "Mediterranean",
        "Mexican",
        "Middle Eastern",
        "Nordic",
        "Southern",
        "Spanish",
        "Thai",
        "Vietnamese"
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // First column
            Column(modifier = Modifier.weight(1f)) {
                cuisines.subList(0, cuisines.size / 2).forEach { cuisine ->
                    CheckboxOption(cuisine, selectedCuisines, onCuisineSelected)
                }
            }

            // Spacer for some separation
            Spacer(modifier = Modifier.width(16.dp))

            // Second column
            Column(modifier = Modifier.weight(1f)) {
                cuisines.subList(cuisines.size / 2, cuisines.size).forEach { cuisine ->
                    CheckboxOption(cuisine, selectedCuisines, onCuisineSelected)
                }
            }
        }
    }
}

@Composable
fun CheckboxOption(cuisine: String, selectedCuisines: MutableState<Set<String>>, onCuisineSelected: (String) -> Unit) {
    val isChecked = selectedCuisines.value.contains(cuisine)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onCuisineSelected(cuisine)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null // We handle the change in the Row's clickable modifier
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = cuisine, style = MaterialTheme.typography.bodySmall)
    }
}






@Composable
fun CuisineGrid(selectedCuisines: MutableState<List<String>>, onCuisineSelected: (String) -> Unit) {
    val cuisines = listOf(
        "African",
        "Asian",
        "American",
        "British",
        "Cajun",
        "Caribbean",
        "Chinese",
        "Eastern European",
        "European",
        "French",
        "German",
        "Greek",
        "Indian",
        "Irish",
        "Italian",
        "Japanese",
        "Jewish",
        "Korean",
        "Latin American",
        "Mediterranean",
        "Mexican",
        "Middle Eastern",
        "Nordic",
        "Southern",
        "Spanish",
        "Thai",
        "Vietnamese"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4), // 2 columns for example
        modifier = Modifier.fillMaxWidth()
    ) {
        items(cuisines) { cuisine ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        if (selectedCuisines.value.contains(cuisine)) Color.Blue else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        if (selectedCuisines.value.contains(cuisine)) {
                            selectedCuisines.value = selectedCuisines.value - cuisine
                        } else {
                            selectedCuisines.value = selectedCuisines.value + cuisine
                        }
                        onCuisineSelected(cuisine)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = cuisine,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuisineDropdown(selectedCuisine: MutableLiveData<String>, onCuisineSelected: (String) -> Unit) {
    val context = LocalContext.current
    val cuisines = listOf(
        "African",
        "Asian",
        "American",
        "British",
        "Cajun",
        "Caribbean",
        "Chinese",
        "Eastern European",
        "European",
        "French",
        "German",
        "Greek",
        "Indian",
        "Irish",
        "Italian",
        "Japanese",
        "Jewish",
        "Korean",
        "Latin American",
        "Mediterranean",
        "Mexican",
        "Middle Eastern",
        "Nordic",
        "Southern",
        "Spanish",
        "Thai",
        "Vietnamese"
    )
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedCuisine.value ?: "Select Cuisine",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                cuisines.forEach { cuisine ->
                    DropdownMenuItem(
                        text = { Text(text = cuisine) },
                        onClick = {
                            selectedCuisine.value = cuisine
                            onCuisineSelected(cuisine)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun DietRadioButton(selectedDiet: MutableLiveData<String>, onDietSelected: (String) -> Unit) {
    val diets = listOf("Vegetarian", "Vegan", "Paleo", "Keto") // Add more as needed

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        diets.forEach { diet ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (diet == selectedDiet.value),
                        onClick = {
                            selectedDiet.value = diet
                            onDietSelected(diet)
                        }
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = (diet == selectedDiet.value),
                    onClick = {
                        selectedDiet.value = diet
                        onDietSelected(diet)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = diet)
            }
        }
    }
}*/