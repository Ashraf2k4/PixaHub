package com.progress.photos.pixahub.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.progress.photos.pixahub.mvvm.MainViewModel
import com.progress.photos.pixahub.R
import com.progress.photos.pixahub.mvvm.SharedViewModel

@Composable
fun SearchScreen(navController: NavController, sharedViewModel: SharedViewModel)
{

    val bgColor = colorResource(id = R.color.green)

    val searchViewModel : MainViewModel = viewModel()
    val images by searchViewModel.imageState

    val search = sharedViewModel.searchText

    LaunchedEffect(Unit) {
        searchViewModel.fetchImagesIfNeeded(listOf(search))
    }

    Box(modifier = Modifier.fillMaxSize()){
        when {
            images.loading ->{
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            images.error != null ->{
                Text(images.error!!,modifier = Modifier.align(Alignment.Center))
            }
            images.list.isEmpty() ->{
                images.error?.let { Text(it,modifier = Modifier.align(Alignment.Center)) }
            }
            else ->{
                sharedViewModel.setPath("search")
                Column(modifier = Modifier.fillMaxSize()){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = bgColor)
                        .height(75.dp)
                        .padding(5.dp)){

                        Box(modifier = Modifier.fillMaxSize())
                        {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = Color.Black,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(35.dp)
                                    .align(Alignment.CenterStart)
                                    .clickable {
                                        navController.navigate("explore") {
                                            launchSingleTop = true
                                            popUpTo("explore") { inclusive = false }
                                        }
                                    })
                            Row(modifier = Modifier.align(Alignment.Center))
                            {
                                Text(text = "Search result for :  ", fontSize = 20.sp, color = Color.Black)
                                Text(
                                    text = sharedViewModel.searchText,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                            Image(imageVector = Icons.Outlined.FilterAlt, contentDescription = null, modifier = Modifier
                                .padding(5.dp)
                                .size(35.dp)
                                .align(Alignment.CenterEnd)
                                .clickable { sharedViewModel.setFilter(true) })
                        }

                    }

                    PreviewScreen(
                        categories = images.list,
                        navController = navController,
                        sharedViewModel = sharedViewModel
                    )
                }

            }
        }
        if(sharedViewModel.fetchFilter){
            FilterOptions(sharedViewModel, modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .fillMaxWidth()
                .background(color = Color.White)
                .align(Alignment.Center))
        }
    }
}
