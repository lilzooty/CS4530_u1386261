package com.example.a5marblegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
class MainActivity : ComponentActivity() {
    private val viewModel: MarbleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the view model with sensor manager
        viewModel.initialize(this)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MarbleScreen(viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.registerSensor()
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterSensor()
    }
}

@Composable
fun MarbleScreen(viewModel: MarbleViewModel) {
    val marbleState by viewModel.marbleState.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight

        viewModel.updateScreenConstraints(maxWidth.value, maxHeight.value)


        Text(
            text = "tilt your device to move the marble",
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            color = Color.DarkGray
        )
        Box(
            modifier = Modifier
                .offset(marbleState.x.dp, marbleState.y.dp)
                .size(40.dp)
                .background(Color.Magenta, CircleShape)
        )

    }
}