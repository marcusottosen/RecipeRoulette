package com.example.reciperoulette.ui.view.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.reciperoulette.data.util.FilterItem
import com.example.reciperoulette.data.util.NavigationRoute
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch
import kotlin.math.ceil


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun FilterPage(navController: NavHostController, viewModel: RecipeViewModel) {
    // Retrieve the filter data
    val filterData = viewModel.filterData.observeAsState()

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    filterData.value?.let { viewModel.applyFilters(
                        filterData.value!!.selectedCuisines,
                        filterData.value!!.selectedIntolerances,
                        filterData.value!!.selectedDiets
                    ) }
                    navController.navigate(NavigationRoute.Homepage.route)

                    //val selectedCuisines = filterData.value?.selectedCuisines?.map { it.name }
                    //val selectedIntolerances = filterData.value?.selectedIntolerances?.map { it.name }
                    //val selectedDiets = filterData.value?.selectedDiets?.map { it.name }

                    //// Print the chosen filters to the console
                    //println("Selected Cuisines: $selectedCuisines")
                    //println("Selected Intolerances: $selectedIntolerances")
                    //println("Selected Diets: $selectedDiets")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Apply Filters")
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp, 0.dp, 16.dp, 0.dp)
            ) {
                item {
                    Row (Modifier.fillMaxWidth().padding(top = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,){
                        Text(
                            text = "Filters",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Text(
                            text = "Reset Filters",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 5.dp)
                                .clickable {
                                    viewModel.resetFilters() // Call the resetFilters function
                                    navController.navigate(NavigationRoute.FilterPage.route)
                                }
                        )
                    }
                }
                item {
                    Text(text = "Cuisines", style = MaterialTheme.typography.titleSmall)
                    Box(modifier = Modifier.height(250.dp)) {
                        filterData.value?.let { ScrollableGrid(it.cuisines) }
                    }
                }
                item {
                    Text(text = "Diets", style = MaterialTheme.typography.titleSmall)
                    Box(modifier = Modifier.height(300.dp)) {
                        filterData.value?.let { CheckboxGrid(filterItems = it.diets, 3) }
                    }
                }
                item {
                    Text(text = "Intolerances", style = MaterialTheme.typography.titleSmall)
                    Box(modifier = Modifier.height(300.dp)) {
                        filterData.value?.let { CheckboxGrid(filterItems = it.intolerances, 3) }
                    }
                }
                item {
                    //SubList2()
                }
            }
        }
    )
}


@Composable
fun CircularCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(if (checked) MaterialTheme.colorScheme.primary else Color.White)
            .border(
                width = 2.dp,
                color = if (checked) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = CircleShape
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center,
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

/*
@Composable
fun CheckboxScrollRow(cuisines: List<FilterItem>) {
    val state = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    Column {


        LazyHorizontalGrid(
            state = state,
            rows = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
            //.background(Color.LightGray)
        ) {
            items(cuisines) { cuisine ->
                var isSelected by remember { mutableStateOf(cuisine.isSelected) }

                Row(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxHeight()
                        .width(120.dp)
                ) {
                    CircularCheckbox(
                        checked = isSelected,
                        onCheckedChange = { isChecked ->
                            isSelected = isChecked // Update the state
                            cuisine.isSelected =
                                isChecked // Update the original Cuisine object's state
                        }
                    )
                    Text(
                        text = cuisine.name,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

        }

    }
}*/



@Composable
fun CheckboxGrid(filterItems: List<FilterItem>, columns: Int = 3) {
    val rows = ceil(filterItems.size / columns.toFloat()).toInt()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(rows) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (columnIndex in 0 until columns) {
                    val index = rowIndex * columns + columnIndex
                    if (index < filterItems.size) {
                        val filterItem = filterItems[index]
                        var isSelected by remember { mutableStateOf(filterItem.isSelected) }

                        // Use Modifier.weight to ensure equal sizes for Checkbox and Text
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp, bottom = 8.dp)
                        ) {
                            CircularCheckbox(
                                checked = isSelected,
                                onCheckedChange = { isChecked ->
                                    isSelected = isChecked
                                    filterItem.isSelected = isChecked
                                }
                            )
                            Text(
                                text = filterItem.name,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    } else {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.SubList2(){
    items(0){
        Text(text = "Hello World", Modifier.padding(16.dp))
    }
}



@Composable
fun ScrollableGrid(cuisines: List<FilterItem>) {
    val itemsPerRow = 9
    val rows = (cuisines.size + itemsPerRow - 1) / itemsPerRow // Calculate the number of rows needed
    val state = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column {

        // Create a scrollable container
        Box(
            modifier = Modifier
                .height(200.dp)
                .horizontalScroll(state = state)
        ) {
            Column {
                for (i in 0 until rows) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (j in i * itemsPerRow until minOf((i + 1) * itemsPerRow, cuisines.size)) {
                            val cuisine = cuisines[j]
                            var isSelected by remember { mutableStateOf(cuisine.isSelected) }

                            Row(
                                modifier = Modifier
                                    .padding(end = 8.dp, top = 12.dp)
                                    .width(120.dp)
                                    .height(50.dp)
                            ) {
                                CircularCheckbox(
                                    checked = isSelected,
                                    onCheckedChange = { isChecked ->
                                        isSelected = isChecked
                                        cuisine.isSelected = isChecked
                                    }
                                )
                                Text(
                                    text = cuisine.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        // Navigation buttons
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(end = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                // IconButton to navigate to the start
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            state.animateScrollTo(0)
                        }
                    },

                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Scroll to Start"
                    )
                }
                // IconButton to navigate to the end
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            state.animateScrollTo(state.maxValue)
                        }
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Scroll to End"
                    )
                }
            }
        }
    }
}



