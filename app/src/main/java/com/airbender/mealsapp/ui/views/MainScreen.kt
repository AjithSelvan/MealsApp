package com.airbender.mealsapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.airbender.mealsapp.model.response.MealsResponse
import com.airbender.mealsapp.viewmodel.MealsViewModel


@Composable
fun InitialScreen() {
    val viewModel = MealsViewModel()
    val meals = viewModel.meals
    LazyColumn {
        items(meals.value) { meal ->
            MealsDetailsScreen(meal)
        }
    }
}

@Composable
fun MealsDetailsScreen(meal: MealsResponse) {
    val cardElevation: CardElevation = CardDefaults.cardElevation(15.dp)
    val cardColors =
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    Card(elevation = cardElevation, colors = cardColors, modifier = Modifier.padding(4.dp), shape = CardDefaults.elevatedShape) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(meal.imageUrl)
                        .crossfade(true).build(),
                    contentDescription = "Meal Description",
                )
                Text(
                    text = meal.category,
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    InitialScreen()
}