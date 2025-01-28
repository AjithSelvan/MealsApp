package com.airbender.mealsapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.airbender.mealsapp.model.response.MealsResponse

@Composable
fun MealsDetailsScreen(meal: MealsResponse?) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Card(
            elevation = CardDefaults.outlinedCardElevation(10.dp),
            shape = CardDefaults.outlinedShape,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(meal?.imageUrl)
                    .transformations(CircleCropTransformation())
                    .crossfade(true).build(),
                contentDescription = "Meal Image",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Card(
            elevation = CardDefaults.outlinedCardElevation(10.dp),
            shape = CardDefaults.elevatedShape,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(modifier = Modifier
                .padding(40.dp)
                .align(Alignment.CenterHorizontally)) {
                meal?.category?.let {
                    Text(
                        text = it,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                }
            }
            meal?.description?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }

}