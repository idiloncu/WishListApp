package com.example.wishlistapp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(viewModel: WishViewModel=viewModel(),
               navController: NavHostController=rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeView(navController,viewModel)
        }
        composable(Screen.AddScreen.route) {
            AddEditDetailView(id = 0L, viewModel, navController)
        }


    }
}