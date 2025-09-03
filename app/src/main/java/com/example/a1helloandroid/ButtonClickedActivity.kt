package com.example.a1helloandroid

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ButtonClickedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //basic setup stuff that I assume we should keep
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_button_clicked)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // get the text passed from the button clicked in MainActivity and put it in the textview
        val textView: TextView = findViewById(R.id.textView)
        val buttonText = intent.getStringExtra("button_text")
        textView.text = buttonText

        // sets the back button to close the current activity, going back to the main activity screen
        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
// this comment was added to make sure theres no bullshit going on with git