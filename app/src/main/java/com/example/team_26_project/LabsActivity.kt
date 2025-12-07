package com.example.team_26_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LabsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labs)

        val module1Card: View = findViewById(R.id.module1)
        val title1: TextView = module1Card.findViewById(R.id.tvVulnTitle)
        title1.text = "Intro to Android Security"
        val read1: Button = module1Card.findViewById(R.id.btnReadLab)
        read1.setOnClickListener {
            val obad= Intent(this, intro_to_android_security::class.java)
            startActivity(obad)

        }

    }
}