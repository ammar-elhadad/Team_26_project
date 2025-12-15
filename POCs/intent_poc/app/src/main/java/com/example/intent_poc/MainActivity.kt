package com.example.intent_poc

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val clickButton = findViewById<Button>(R.id.button)
        clickButton.text = "Click Me"
        clickButton.setOnClickListener {

            val intent = Intent().apply {
                setClassName(
                    "com.example.team_26_project",
                    "com.example.team_26_project.Admin"
                )
                putExtra("username", "obad")
                //addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            startActivity(intent)

        }

    }
}