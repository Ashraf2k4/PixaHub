package com.progress.photos.pixahub.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.progress.photos.pixahub.mvvm.SharedViewModel
import com.progress.photos.pixahub.apipack.Hit

@Composable
fun PreviewScreen(categories: List<Hit>, navController: NavController, sharedViewModel: SharedViewModel)
{

    val shouldShowLandscapeImages: (Hit) -> Boolean = { image ->
        image.imageHeight < image.imageWidth
    }
    val shouldShowPortraitImages: (Hit) -> Boolean = { image ->
        image.imageHeight > image.imageWidth
    }

    val filteredImages : List<Hit> = when (sharedViewModel.orientationType) {
        "Landscape Images" -> {
            categories.filter(shouldShowLandscapeImages)
        }
        "Portrait Images" -> {
            categories.filter(shouldShowPortraitImages)
        }
        else -> {
            categories
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        )
    {
        if(sharedViewModel.orientationType == "Landscape Images" && filteredImages.isEmpty()){
            Text(text = "No landscape image found.", color= Color.Black)
        }
        if(sharedViewModel.orientationType == "Portrait Images" && filteredImages.isEmpty()){
            Text(text = "No portrait image found.", color= Color.Black)
        }
        if(sharedViewModel.viewType == "ListView") {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                contentPadding = PaddingValues(0.dp)
            ) {
                items(filteredImages) { category ->
                    Card(
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(15.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 15.dp,
                            pressedElevation = 10.dp,
                            hoveredElevation = 5.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(325.dp)
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .clickable {
                                sharedViewModel.selectCategory(category)
                                sharedViewModel.setPath("home")
                                navController.navigate("preview") {
                                    launchSingleTop = true
                                    popUpTo("preview") { inclusive = false }
                                }
                            }

                    ) {
                        Column(modifier = Modifier.fillMaxWidth())
                        {
                            Box(modifier = Modifier.padding(5.dp)) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.CenterStart)
                                        .height(50.dp)
                                        .padding(horizontal = 10.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(40.dp)
                                    ) {
                                        if (category.userImageURL.isNotEmpty()) {
                                            AsyncImage(
                                                model = category.userImageURL,
                                                contentDescription = null
                                            )
                                        } else {
                                            Image(
                                                imageVector = Icons.Default.AccountCircle,
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = category.user,
                                        fontSize = 20.sp,
                                        color = Color.Black
                                    )
                                }
                                Image(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = null,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                            AsyncImage(
                                model = category.largeImageURL,
                                contentDescription = category.tags,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .height(350.dp)
                            )
                        }
                    }
                }

            }
        }
        else if(sharedViewModel.viewType == "GridView"){
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(filteredImages) { category ->
                    Card(
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(15.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 15.dp,
                            pressedElevation = 10.dp,
                            hoveredElevation = 5.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(2.dp)
                            .clickable {
                                sharedViewModel.selectCategory(category)
                                sharedViewModel.setPath("home")
                                navController.navigate("preview") {
                                    launchSingleTop = true
                                    popUpTo("preview") { inclusive = false }
                                }
                            }

                    ) {
                        AsyncImage(
                            model = category.largeImageURL,
                            contentDescription = category.tags,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .height(350.dp)
                        )
                    }
                }
            }
        }
    }
}