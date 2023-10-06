package com.example.oneduty

import ProfileScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oneduty.sign_in.UserData

@Composable
fun AppLayOutScreen(navController: NavHostController, userData: UserData?) {
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        Box(Modifier.padding(it)){
            NavigationGraph(navController = navController, userData = userData)
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, userData: UserData?) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            ProfileScreen(userData = userData)
        }
//        composable("board") {
//            TimelineScreen()
//        }
//        composable("profile") {
//            AnalysisScreen()
//        }
    }
}