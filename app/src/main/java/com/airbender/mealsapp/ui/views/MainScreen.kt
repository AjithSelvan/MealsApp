package com.airbender.mealsapp.ui.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.airbender.mealsapp.model.response.MealsResponse
import com.airbender.mealsapp.viewmodel.MealsViewModel


@Composable
fun InitialScreen(navCallBack: (String) -> Unit) {
    val viewModel = MealsViewModel()
    val meals = viewModel.meals
    LazyColumn(modifier = Modifier.statusBarsPadding()) {
        items(meals.value) { meal ->
            MealsDetailsScreen(meal, navCallBack)
        }
    }
}

@Composable
fun MealsDetailsScreen(meal: MealsResponse, navCallBack: (String) -> Unit) {
    val cardElevation: CardElevation = CardDefaults.cardElevation(15.dp)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cardColors =
        CardDefaults.cardColors(containerColor = if (isPressed) Color.Gray else Color.White)
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        elevation = cardElevation, colors = cardColors,
        modifier = Modifier
            .padding(4.dp)
            .border(BorderStroke(4.dp, color = Color.White))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { navCallBack(meal.id) },
        shape = CardDefaults.elevatedShape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(meal.imageUrl)
                    .crossfade(true).build(),
                contentDescription = "Meal Description",
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = meal.category,
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                )

                CompositionLocalProvider(value = LocalContentColor provides MaterialTheme.colorScheme.surfaceVariant) {
                    Text(
                        text = meal.description,
                        modifier = Modifier
                            .padding(all = 4.dp)
                            .align(Alignment.Start),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (isExpanded) 10 else 4
                    )
                }
            }
            Icon(imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Upward Arrow",
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    .clickable { isExpanded = !isExpanded })
        }
    }
}


@Preview
@Composable
fun Preview() {
    InitialScreen {}
}