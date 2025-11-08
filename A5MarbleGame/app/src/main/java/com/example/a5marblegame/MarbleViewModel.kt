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

class MarbleViewModel : ViewModel(), SensorEventListener{
    // Screen boundaries
    private var maxX = null
    private var maxY = null
    private val marbleSize = 40f

    // Sensor variables
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null

    //Math Constants
    private val frictionCoefficient = 0.95f

    // Marble state variables
    private val _marbleState = MutableStateFlow(Marble())
    val marbleState: StateFlow<Marble> = _marbleState.asStateFlow()

    // Math state
    private var velocityX = 0f
    private var velocityY = 0f

    fun initialize(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val gravitySensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY)
        if (gravitySensor != null) {
            sensor = gravitySensor
        } else {
            sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
    }



        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        TODO("Not yet implemented")

    }


}