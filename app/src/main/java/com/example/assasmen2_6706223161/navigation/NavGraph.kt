package com.example.assasmen2_6706223161.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.asasmen_1.ui.screen.DescScreen
import com.example.assasmen2_6706223161.ui.screen.AboutScreen
import com.example.assasmen2_6706223161.ui.screen.DetailScreen
import com.example.assasmen2_6706223161.ui.screen.MainScreen
import com.example.assasmen2_6706223161.ui.screen.PesanScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Katalog.route
    ) {
        composable(route = Screen.About.route){
            AboutScreen(navController)
        }
        composable(route = Screen.Desc.route){
            DescScreen(navController)
        }
        composable(route = Screen.Katalog.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(route = Screen.Pesan.route) {
            PesanScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val id = arguments.getLong("id")
            DetailScreen(navController, id)
        }
    }
}