package com.progress.photos.pixahub.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.progress.photos.pixahub.R
import com.progress.photos.pixahub.mvvm.SharedViewModel

@Composable
fun ExploreScreen(navController : NavController, sharedViewModel : SharedViewModel)
{
    var checkDone by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val sportList = listOf(R.drawable.cricket, R.drawable.football, R.drawable.hockeyjpeg, R.drawable.kabaddi, R.drawable.boxing)
    val sportName = listOf("Cricket","Football", "Hockey", "Kabaddi", "Boxing")

    val tourList = listOf(R.drawable.hiking, R.drawable.roadtrip, R.drawable.camping, R.drawable.safari)
    val tourName = listOf("Hiking", "Roadtrip", "Camping", "Safari")

    val artList = listOf(R.drawable.dancing, R.drawable.singing, R.drawable.drama, R.drawable.music, R.drawable.circus)
    val artName = listOf("Dancing", "Singing", "Drama", "Music", "Circus")

    val natureList = listOf(R.drawable.mountain, R.drawable.riverjpg, R.drawable.clouds, R.drawable.forest)
    val natureName = listOf("Mountain", "River", "Clouds", "Forest")

    if(sharedViewModel.searchFocus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Column(modifier = Modifier.background(color = Color.White))
    {
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors( focusedTextColor = Color.Black),
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 3.dp)
                .focusRequester(focusRequester),
            placeholder = @Composable { Text(text = "Search images by tags...") },
            trailingIcon = {
                if (search.isNotEmpty()) {
                    Image(imageVector = Icons.Filled.Clear, contentDescription = null,
                        modifier = Modifier.clickable {
                            if (search.isNotEmpty()) search = ""
                        }
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    sharedViewModel.setSearchFocus(false)
                    checkDone = true
                    if(search.isNotEmpty()){
                        sharedViewModel.setSearchText(search)
                        navController.navigate("search"){
                            launchSingleTop = true
                            popUpTo("search") { inclusive = false }
                        }
                        search = ""
                    }
                }
            ))

        LazyColumn {
            item{
                CreateCategory(sportList, sportName, "Sports", sharedViewModel, navController)
            }
            item{
                CreateCategory(
                    imageList = tourList,
                    nameList = tourName,
                    "Tour & Travel",
                    sharedViewModel,
                    navController
                )
            }
            item{
                CreateCategory(
                    imageList = artList,
                    nameList = artName,
                    "Art",
                    sharedViewModel,
                    navController
                )
            }
            item{
                CreateCategory(
                    imageList = natureList,
                    nameList = natureName,
                    "Nature",
                    sharedViewModel,
                    navController
                )
            }
        }
    }
}

@Composable
fun CreateCategory(imageList: List<Any>, nameList: List<String>, type: String, sharedViewModel: SharedViewModel, navController: NavController)
{
    Spacer(modifier = Modifier.height(8.dp))
    Card(
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(15.dp),
        elevation =  CardDefaults.cardElevation(
            defaultElevation = 15.dp,
            pressedElevation = 10.dp,
            hoveredElevation = 5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Text(text = "$type : ", modifier = Modifier.padding(start = 10.dp, top= 10.dp, bottom = 5.dp), color = Color.Black)
        CategoryList(imageList,nameList,sharedViewModel,navController)
    }
}


@Composable
fun CategoryList(imageList: List<Any>, imageName: List<String>, sharedViewModel: SharedViewModel, navController: NavController)
{

    LazyRow(modifier = Modifier.padding(5.dp)) {
        items(imageList.size){
            index->
            Card(
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(15.dp),
                elevation =  CardDefaults.cardElevation(
                    defaultElevation = 15.dp,
                    pressedElevation = 10.dp,
                    hoveredElevation = 5.dp),
                modifier = Modifier
                    .width(150.dp)
                    .height(130.dp)
                    .padding(horizontal = 8.dp, vertical = 5.dp)
                    .clickable {
                        sharedViewModel.setSearchText(imageName[index])
                        navController.navigate("search"){
                            launchSingleTop = true
                            popUpTo("search") { inclusive = false }
                        }
                    }
            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(model = imageList[index], contentDescription = null, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = imageName[index], color = Color.Black)
                }
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(15.dp),
                elevation =  CardDefaults.cardElevation(
                    defaultElevation = 15.dp,
                    pressedElevation = 10.dp,
                    hoveredElevation = 5.dp),
                modifier = Modifier
                    .width(150.dp)
                    .height(130.dp)
                    .padding(horizontal = 8.dp, vertical = 5.dp)
                    .clickable {
                        sharedViewModel.setSearchFocus(focus = true)
                    }
            ){
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                    ) {
                    Image(imageVector = Icons.Default.Search, contentDescription = null,)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "search", color = Color.Black)
                }

            }
        }

    }
}