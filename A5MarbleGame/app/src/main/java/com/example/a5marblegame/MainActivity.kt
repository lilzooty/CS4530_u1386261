package com.example.a5marblegame

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
class MainActivity : ComponentActivity() {
    private val viewModel: MarbleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Initialize the view model with sensor manager
        viewModel.initialize(this)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
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

    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .onSizeChanged { size ->
            viewModel.updateScreenConstraints(
                size.width.toFloat(),
                size.height.toFloat()
            )
        }
    )
    {
        val maxWidth = maxWidth
        val maxHeight = maxHeight
        viewModel.updateScreenConstraints(maxWidth.value, maxHeight.value)

        Box(
            modifier = Modifier
                .offset((marbleState.x - 20).dp, (marbleState.y - 20).dp)
                .size(40.dp)
                .background(Color.Magenta, CircleShape)
        )
    }
}