package com.example.reciperoulette.ui.view.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reciperoulette.data.util.FilterItem
import com.example.reciperoulette.data.util.NavigationRoute
import com.example.reciperoulette.data.util.filterdata
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel
import kotlin.math.ceil


@ExperimentalFoundationApi
@Composable
fun FilterPageTwo(navController: NavController, viewModel: RecipeViewModel) {
    val filterData = filterdata()   // Retrieve the filter data


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(text = "Cuisines", style = MaterialTheme.typography.titleMedium)
            Box(modifier = Modifier.height(200.dp)) {
                CheckboxScrollRow(filterData.cuisines)
            }
        }

        item {
            Text(text = "Diets", style = MaterialTheme.typography.titleMedium)
            Box(modifier = Modifier.height(300.dp)) {
                CheckboxGrid(filterItems = filterData.diets, 3)
            }
        }

        item {
            Text(text = "Intolerances", style = MaterialTheme.typography.titleMedium)
            Box(modifier = Modifier.height(300.dp)) {
                CheckboxGrid(filterItems = filterData.intolerances, 3)
            }
        }
        item{
            Button(
                onClick = {
                    viewModel.applyFilters(filterData.selectedCuisines, filterData.selectedIntolerances, filterData.selectedDiets)
                    navController.navigate(NavigationRoute.Homepage.route)

                    val selectedCuisines = filterData.selectedCuisines.map { it.name }
                    val selectedIntolerances = filterData.selectedIntolerances.map { it.name }
                    val selectedDiets = filterData.selectedDiets.map { it.name }

                    // Print the chosen filters to the console
                    println("Selected Cuisines: $selectedCuisines")
                    println("Selected Intolerances: $selectedIntolerances")
                    println("Selected Diets: $selectedDiets")
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Apply Filters")
            }
        }

        SubList2()

    }
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

@Composable
fun CheckboxScrollRow(cuisines: List<FilterItem>) {
    LazyHorizontalGrid(
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
                        cuisine.isSelected = isChecked // Update the original Cuisine object's state
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
                            modifier = Modifier.weight(1f).padding(end = 8.dp, bottom = 8.dp)
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
    items(5){
        Text(text = "Hello World", Modifier.padding(16.dp))
    }
}

@Composable
fun FilterPageTwo2() {
    Column (Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            FilterGrid()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            FilterGrid()

        }
    }

    /*LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text("Filters", style = MaterialTheme.typography.titleMedium)
        }

        item {
            FilterGrid()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Filters 2", style = MaterialTheme.typography.titleMedium)
        }

        item {
            FilterGrid()
        }
    }*/
}
@Composable
fun FilterGrid() {
    var checkedStates by remember { mutableStateOf(List(20) { false }) }

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        items(20) { index ->
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
                    //.width(160.dp) // Adjust the width as needed
            ) {
                Checkbox(
                    checked = checkedStates[index],
                    onCheckedChange = { isChecked ->
                        checkedStates = checkedStates.toMutableList().also {
                            it[index] = isChecked
                        }
                    }
                )
                BasicTextField(
                    value = "Item $index",
                    onValueChange = {},
                    textStyle = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
    }
}
