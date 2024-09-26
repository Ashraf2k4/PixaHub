package com.progress.photos.pixahub.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.progress.photos.pixahub.R
import com.progress.photos.pixahub.mvvm.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(navController: NavController, sharedViewModel: SharedViewModel) {

    val bgColor = colorResource(id = R.color.green)

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Downloads",
                    modifier = Modifier.padding(start = 15.dp),
                    fontSize = 25.sp)
            },
            colors = TopAppBarDefaults.topAppBarColors(bgColor),
            actions = {
                Image(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                sharedViewModel.setSearchFocus(focus = true)
                                navController.navigate("explore"){
                                    launchSingleTop = true
                                    popUpTo("hots") { inclusive = false}
                                } }
                            .size(50.dp)
                            .padding(8.dp)
                    )
                }
            )
        }
    )
    {
            paddingValue->
        Column(modifier = Modifier
            .padding(paddingValue)
            .fillMaxSize()) {
        }
    }
}