package com.example.a5marblegame

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MarbleViewModel : ViewModel(), SensorEventListener {
    // Screen boundaries
    private var screenWidth: Float = 0f
    private var screenHeight: Float = 0f
    private val marbleRadius = 50f

    // Sensor variables
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null

    // Math Constants
    private val frictionCoefficient = 0.8f
    private val gravityScale = 1.2f

    // Marble state variables
    private val _marbleState = MutableStateFlow(Marble())
    val marbleState: StateFlow<Marble> = _marbleState.asStateFlow()
    private var velocityX = 0f
    private var velocityY = 0f

    fun initialize(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val gravitySensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY)
        sensor = gravitySensor ?: sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    fun updateScreenConstraints(width: Float, height: Float) {
        screenWidth = width
        screenHeight = height

        // Initialize marble at center if it's at origin
        if (_marbleState.value.x == 0f && _marbleState.value.y == 0f) {
            _marbleState.value = _marbleState.value.copy(
                x = width / 2,
                y = height / 2
            )
        }
    }

    fun registerSensor() {
        sensor?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun unregisterSensor() {
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed so I think I can just leave it blank
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || screenWidth <= 0f || screenHeight <= 0f) return

        if (event.sensor.type == Sensor.TYPE_GRAVITY ||
            event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val gx = event.values[0]
            val gy = event.values[1]
            val tiltX = -gx
            val tiltY = gy

            updatePhysics(tiltX, tiltY)
        }
    }

    private fun updatePhysics(gravityX: Float, gravityY: Float) {
        // Update velocity based on gravity
        velocityX += gravityX * gravityScale
        velocityY += gravityY * gravityScale

        // Apply friction
        velocityX *= frictionCoefficient
        velocityY *= frictionCoefficient

        // Update position
        var newX = _marbleState.value.x + velocityX
        var newY = _marbleState.value.y + velocityY

        // Keep within boundaries
        val minX = marbleRadius
        val maxX = screenWidth - marbleRadius
        val minY = marbleRadius
        val maxY = screenHeight - marbleRadius

        if (newX < minX) {
            newX = minX
            velocityX = 0f
        } else if (newX > maxX) {
            newX = maxX
            velocityX = 0f
        }

        if (newY < minY) {
            newY = minY
            velocityY = 0f
        } else if (newY > maxY) {
            newY = maxY
            velocityY = 0f
        }

        // Update state
        _marbleState.value = _marbleState.value.copy(x = newX, y = newY)
    }
}