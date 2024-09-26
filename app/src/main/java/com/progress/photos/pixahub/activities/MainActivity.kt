package com.progress.photos.pixahub.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.progress.photos.pixahub.mvvm.SharedViewModel
import com.progress.photos.pixahub.screen.ExploreScreen
import com.progress.photos.pixahub.screen.HomeScreen
import com.progress.photos.pixahub.screen.MoreScreen
import com.progress.photos.pixahub.screen.DetailScreen
import com.progress.photos.pixahub.screen.SearchScreen
import com.progress.photos.pixahub.ui.theme.PixaHubTheme

class MainActivity : ComponentActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixaHubTheme {
                Surface(modifier = Modifier.fillMaxSize()){
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen()
{

    val navController = rememberNavController()

    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController = navController, sharedViewModel)}
        composable("explore") { ExploreScreen(navController = navController, sharedViewModel) }
        composable("search") { SearchScreen(navController = navController, sharedViewModel)}
        composable("preview") { DetailScreen(navController = navController,sharedViewModel)}
        composable("download") { MoreScreen(navController = navController,sharedViewModel)}
    }

}


@Preview(showBackground = true)
@Composable
fun Show(){
    MainScreen()
}
