package com.example.oneduty

import CalendarScreen
import ProfileScreen
import SettingsScreen
import TimelineScreen
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.oneduty.sign_in.UserData
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigation(navController: NavController, userData: UserData?) {
    val items = listOf(
        BottomNavItem.Calendar,
        BottomNavItem.Timeline,
        BottomNavItem.Settings
    )


        var navigationSelectedItem by remember {
            mutableStateOf(0)
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed {index, item ->
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            onClick = {
                                navigationSelectedItem = index
                                navController.navigate(item.screenRoute) {
                                    navController.graph.startDestinationRoute?.let { screenRoute ->
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            },
                            label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
                            alwaysShowLabel = false,
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = stringResource(id = item.title),
                                    modifier = Modifier
                                        .width(26.dp)
                                        .height(26.dp)
                                )
                            },
                        )
                    }
                }
            }
        ) {
            NavHost(navController = navController  as NavHostController, startDestination = BottomNavItem.Calendar.screenRoute) {
                composable(BottomNavItem.Calendar.screenRoute) {
                    CalendarScreen(userData = userData)
                }
                composable(BottomNavItem.Timeline.screenRoute) {
                    TimelineScreen()
                }
                composable(BottomNavItem.Settings.screenRoute) {
                    SettingsScreen()
                }
            }
        }

}
