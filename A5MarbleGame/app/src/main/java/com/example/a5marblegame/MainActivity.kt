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
import androidx.compose.ui.platform.LocalDensity
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
    val density = LocalDensity.current
    val marbleRadiusPx = 50F      // in pixels
    val marbleRadiusDp = with(density) { marbleRadiusPx.toDp() }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Convert Compose's maxWidth/maxHeight from dp → pixels for the ViewModel
        val screenWidthPx = with(density) { maxWidth.toPx() }
        val screenHeightPx = with(density) { maxHeight.toPx() }

        // Update the VM once per composition with pixel sizes
        viewModel.updateScreenConstraints(screenWidthPx, screenHeightPx)

        // Draw the marble using the same radius
        Box(
            modifier = Modifier
                .offset(
                    x = with(density) { (marbleState.x - marbleRadiusPx).toDp() },
                    y = with(density) { (marbleState.y - marbleRadiusPx).toDp() }
                )
                .size(marbleRadiusDp * 2)
                .background(Color.Magenta, CircleShape)
        )
    }
}
