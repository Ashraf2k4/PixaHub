package com.progress.photos.pixahub.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.progress.photos.pixahub.R
import kotlinx.coroutines.delay

class FirstActivity :ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val x : () -> Unit  = {
            startActivity(intent)
        }

        setContent {
            SplashScreen(x)
        }
    }
}

@Composable
fun SplashScreen(x:() -> Unit) {

    LaunchedEffect(Unit) {
        delay(4000)
        x()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.green))){
        Column(modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.pixahub), contentDescription = null,
                modifier = Modifier
                    .size(125.dp)
                    .clip(CircleShape))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "PixaHub", color = Color.Black, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

        Row(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 40.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Designed by ", color = Color.Black)
            Text("ASHRAF ALI", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}
