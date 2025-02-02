package com.airbender.mealsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.airbender.mealsapp.ui.theme.MealsAppTheme
import com.airbender.mealsapp.ui.views.InitialScreen
import com.airbender.mealsapp.ui.views.SingleMealsDetailsScreen
import com.airbender.mealsapp.viewmodel.MealsDetailsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealsAppTheme {
                Navigate()
            }
        }
    }
}

@Composable
fun Navigate() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "MainScreen") {
        composable(route = "MainScreen") {
            InitialScreen(navCallBack = { mealId -> navController.navigate("MealDetailsScreen/$mealId") })
        }
        composable(
            route = "MealDetailsScreen/{mealId}",
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) {
            val mealDetailsViewModel : MealsDetailsViewModel = viewModel()
            SingleMealsDetailsScreen(mealDetailsViewModel.mealState.value){
                navController.popBackStack()
            }
        }
    }
}