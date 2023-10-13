package com.example.oneduty

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.oneduty.bottomNavigation.BottomNavigation
import com.example.oneduty.sign_in.UserData

@Composable
fun MainScreenView(userData: UserData?, navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        BottomNavigation(navController = navController, userData = userData)
    }
}


