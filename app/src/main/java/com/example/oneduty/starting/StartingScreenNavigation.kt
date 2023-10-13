package com.example.oneduty.starting

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oneduty.ChooseGroupScreen
import com.example.oneduty.ChooseRoleScreen
import com.example.oneduty.MainScreenView
import com.example.oneduty.sign_in.UserData


@Composable
fun StartingScreenNavigation(userData: UserData?, navController: NavHostController) {
    val navBarController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "choose_role"
    ) {
        composable("choose_role") {
            ChooseRoleScreen(
                userData = userData,
                navController = navController,
            )
        }
        composable("choose_group") {
            ChooseGroupScreen(
                userData = userData,
                navController = navController,
            )
        }
        composable("main") {
            MainScreenView(
                userData = userData,
                navController = navBarController
            )
        }

    }
}
