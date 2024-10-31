package com.progress.photos.pixahub.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.progress.photos.pixahub.R
import com.progress.photos.pixahub.mvvm.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    // Observe the current back stack entry for destination updates
    val currentDestination by navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)

    val sharedViewModel: SharedViewModel = viewModel()

    val bgColor = colorResource(id = R.color.green)

    // These should come from the viewModel directly, no need for remember
    val topBarText by sharedViewModel.viewTopBarText.collectAsState()
    val showActions by sharedViewModel.getActions.collectAsState()

    Scaffold(
        topBar = {
            if (currentDestination?.destination?.route == "home" || currentDestination?.destination?.route == "explore") {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(bgColor),
                    scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                    title = {
                        Text(
                            text = topBarText,
                            modifier = Modifier.padding(start = 15.dp),
                            fontSize = 25.sp,
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        Image(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable { /* Handle menu click */ }
                                .size(35.dp)
                                .padding(start = 5.dp)
                        )
                    },
                    actions = {
                        if (showActions) {
                            Image(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        sharedViewModel.setSearchFocus(true)
                                        navController.navigate("explore") {
                                            launchSingleTop = true
                                            popUpTo("explore") { inclusive = false }
                                        }
                                    }
                                    .size(50.dp)
                                    .padding(8.dp)
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentDestination?.destination?.route == "home" || currentDestination?.destination?.route == "explore") {
                CustomBottomBar(navController, sharedViewModel)
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(navController = navController, sharedViewModel) }
                composable("explore") { ExploreScreen(navController = navController, sharedViewModel) }
                composable("search") { SearchScreen(navController = navController, sharedViewModel) }
                composable("preview") { DetailScreen(navController = navController, sharedViewModel) }
                composable("download") { MoreScreen(navController = navController, sharedViewModel) }
            }
        }
    }
}

@Composable
fun CustomBottomBar(navController: NavController, sharedViewModel: SharedViewModel) {
    // Observe the current back stack entry for destination updates
    val currentDestination by navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)

    val bgColor = colorResource(id = R.color.green)

    var bgHome by remember { mutableStateOf(Color.White) }
    var bgExplore by remember { mutableStateOf(Color.Transparent) }

    fun navigate(route: String) {
        if (currentDestination?.destination?.route != route) {
            navController.navigate(route) {
                launchSingleTop = true
                popUpTo(route) { inclusive = false }
            }
            when (route) {
                "home" -> {
                    bgHome = Color.White
                    bgExplore = Color.Transparent
                    sharedViewModel.setTopBarText("Home")
                    sharedViewModel.setActions(true)
                }
                "explore" -> {
                    bgHome = Color.Transparent
                    bgExplore = Color.White
                    sharedViewModel.setTopBarText("Explore")
                    sharedViewModel.setActions(false)
                }
            }
        }
    }

    BottomAppBar(containerColor = bgColor) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.clickable { navigate("home") },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(vertical = 10.dp)
                        .clip(CircleShape)
                        .background(bgHome)
                )
                Text(text = "HOME", color = Color.Black)
            }
            Column(
                modifier = Modifier.clickable { navigate("explore") },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = Icons.Outlined.Explore,
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(vertical = 10.dp)
                        .clip(CircleShape)
                        .background(bgExplore)
                )
                Text(text = "EXPLORE", color = Color.Black)
            }
        }
    }
}
