package com.example.a1helloandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // basic setup stuff that I probably shouldn't touch
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // setup the click listeners for all buttons
        setClickListener(R.id.button1)
        setClickListener(R.id.button2)
        setClickListener(R.id.button3)
        setClickListener(R.id.button4)
        setClickListener(R.id.button5)
    }

    // sets click listener for a given button using its id
    private fun setClickListener(buttonId: Int) {
        val button: Button = findViewById(buttonId)
        button.setOnClickListener {
            // declare intent to go from main activity to the next
            val intent = Intent(this, ButtonClickedActivity::class.java)
            // sends the button's displayed text to the next activity as a string
            intent.putExtra("button_text", button.text.toString())
            startActivity(intent)
        }
    }
}