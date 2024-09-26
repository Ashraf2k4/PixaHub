package com.progress.photos.pixahub.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.progress.photos.pixahub.mvvm.MainViewModel
import com.progress.photos.pixahub.R
import com.progress.photos.pixahub.mvvm.SharedViewModel
import com.progress.photos.pixahub.apipack.ConnectivityObserver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, sharedViewModel: SharedViewModel)
{
    val bgColor = colorResource(id = R.color.green)

    val context = LocalContext.current
    val connectivityObserver = remember { ConnectivityObserver(context) }
    val isConnected by connectivityObserver.isConnected.collectAsState()

    val imageViewModel: MainViewModel = viewModel()
    val images by imageViewModel.imageState

    val items =
        listOf("Sunset", "Sunrise", "Moon", "Sky", "Nightsky", "Animals", "Cat", "Dog")

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(bgColor),
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                title = {
                Text(text = "Home",
                    modifier = Modifier.padding(start = 15.dp),
                    fontSize = 25.sp,
                    color = Color.Black)},
                navigationIcon = {
                    Image(imageVector = Icons.Filled.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { }
                            .size(35.dp)
                            .padding(start = 5.dp))},
                actions = {
                    Image(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                sharedViewModel.setSearchFocus(focus = true)
                                navController.navigate("explore") {
                                    launchSingleTop = true
                                    popUpTo("explore") { inclusive = false }
                                }
                            }
                            .size(50.dp)
                            .padding(8.dp)
                    )
                }
            )
        },
        bottomBar = { CustomBottomBar(navController) }
    )
    {
        paddingValue->

        if(isConnected) {
            LaunchedEffect(Unit) {
                imageViewModel.fetchImagesIfNeeded(items)
            }
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(paddingValue)
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(bgColor)
                        .height(50.dp)
                        .padding(horizontal = 5.dp)
                ) {
                    Text(
                        text = "TOPICS : ",
                        modifier = Modifier.padding(5.dp),
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                    LazyRow(modifier = Modifier.weight(1f)) {
                        items(items.size) { index ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(Color.White),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .height(35.dp)
                                    .border(
                                        BorderStroke(1.dp, Color.Black),
                                        RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Text(
                                    text = items[index],
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(5.dp),
                                    color = Color.Black
                                )
                            }
                        }
                    }
                    Image(imageVector = Icons.Outlined.FilterAlt,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(35.dp)
                            .clickable { sharedViewModel.setFilter(true) })
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                    when {
                        images.loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }

                        images.error != null -> {
                            Text(images.error!!, modifier = Modifier.align(Alignment.Center))
                        }

                        images.list.isEmpty() -> {
                            images.error?.let {
                                Text(
                                    it,
                                    modifier = Modifier.align(Alignment.Center),
                                    color = Color.Black
                                )
                            }
                        }

                        else -> {
                            PreviewScreen(categories = images.list, navController, sharedViewModel)
                        }
                    }
                    if (sharedViewModel.fetchFilter) {
                        FilterOptions(
                            sharedViewModel, modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .fillMaxWidth()
                                .background(color = Color.White)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
        else{
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Not Connected to Internet", fontSize =20.sp,color = Color.Black)
            }
        }
    }
}

@Composable
fun FilterOptions(sharedViewModel: SharedViewModel, modifier : Modifier)
{

    var selectedOrientation by remember { mutableStateOf(sharedViewModel.orientationType) }
    var selectedView by remember { mutableStateOf(sharedViewModel.viewType) }

    val viewType = listOf("ListView", "GridView")
    val orientation = listOf("Landscape Images", "Portrait Images", "Both")

    Dialog(onDismissRequest = {sharedViewModel.setFilter(false)}) {
        Box(modifier = modifier)
        {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(text = "Select Orientation :", fontSize = 20.sp, color = Color.Black)
                    orientation.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedOrientation = option }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedOrientation == option,
                                onClick = {
                                    selectedOrientation = option
                                },
                                colors = RadioButtonDefaults.colors(colorResource(id = R.color.green))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = option, color = Color.Black)
                        }

                    }
                }
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .height(0.8.dp)
                        .background(color = Color.Black)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                )
                {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(text = "Select View :", fontSize = 20.sp, color = Color.Black)
                    viewType.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedView = option }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedView == option,
                                onClick = {
                                    selectedView = option
                                },
                                colors = RadioButtonDefaults.colors(colorResource(id = R.color.green))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = option, color = Color.Black)
                        }

                    }
                }
                Button(
                    onClick = {
                        sharedViewModel.setView(selectedView)
                        sharedViewModel.setOrientation(selectedOrientation)
                        sharedViewModel.setFilter(false)
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green))
                )
                {
                    Text(text = "Save", color = Color.Black)
                }
            }
        }
    }
}


@Composable
fun CustomBottomBar(navController : NavController)
{

    val bgColor = colorResource(id = R.color.green)

    BottomAppBar( containerColor = bgColor) {
        Row(modifier = Modifier
            .fillMaxSize()
            .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(
                modifier = Modifier.clickable {  },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Image(imageVector = Icons.Filled.Home, contentDescription = null,
                    modifier = Modifier.size(45.dp).padding(vertical = 10.dp).clip(CircleShape).background(color = Color.White))
                Text(text = "HOME", color= Color.Black)
            }
            Column(
                modifier = Modifier.clickable {
                    navController.navigate("explore") {
                    launchSingleTop = true
                    popUpTo("explore") { inclusive = false } }
                } ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    imageVector = Icons.Outlined.Explore,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp).padding(vertical = 10.dp)
                )
                Text(text = "EXPLORE", color= Color.Black)
            }
        }
    }
}

