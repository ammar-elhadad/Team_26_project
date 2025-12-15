package com.example.team_26_project

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DataStorage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_storage)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val saveButton = findViewById<Button>(R.id.save_button)
        val rememberMeCheck = findViewById<CheckBox>(R.id.remember_me_checkbox)


        val PREFS_NAME = "SuperSecretUserCreds"

        saveButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {


                if (rememberMeCheck.isChecked) {
                    val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()


                    editor.putString("username", username)
                    editor.putString("password", password)

                    editor.apply()

                    Toast.makeText(this, "Logged in & Data Saved Insecurely!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Logged in (Data NOT saved)", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
