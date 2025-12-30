package com.example.team_26_project

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.team_26_project.database.AppDatabase
import com.example.team_26_project.database.User
import kotlinx.coroutines.launch
import androidx.sqlite.db.SupportSQLiteDatabase
import android.widget.Toast
import com.example.team_26_project.database.UserDao
class SQL_injection : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var supportDb: SupportSQLiteDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sql_injection)

        db = AppDatabase.getDatabase(this)
        supportDb = db.openHelper.writableDatabase
        userDao = db.userDao()

        lifecycleScope.launch {
            insertUsersIfNeeded()
        }

        val usernameInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString()

            val results = vulnerableQuery(username, password)

            if (results.isNotEmpty()) {
                Toast.makeText(this, "Logged in! \nPwnD{squ3l_1njecti0n}", Toast.LENGTH_LONG).show()
                val sb = StringBuilder("Login Success!\n\nLeaked Account Data:\n--------------------------\n")
                results.forEach {
                    sb.append("User: ${it.first}\nPass: ${it.second}\n--------------------------\n")
                }

                Log.d("SQL_INJECTION_RESULT", sb.toString())

            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show()
                Log.d("SQL_INJECTION_RESULT", "Invalid username or password")
            }
        }
    }

    private suspend fun insertUsersIfNeeded() {

        if (userDao.getUsersCount() == 0) {
            userDao.insertUser(User(username = "admin@gmail.com", password = "1234"))
            userDao.insertUser(User(username = "mariam@gmail.com", password = "alice2025@!"))
            userDao.insertUser(User(username = "ali@gmail.com", password = "b0bP@ssw0rd"))
        }
    }


    private fun vulnerableQuery(username: String, password: String): List<Pair<String, String>> {
        val query = userDao.buildVulnerableLoginQuery(username, password)

        val cursor = supportDb.query(query)
        val list = mutableListOf<Pair<String, String>>()

        while (cursor.moveToNext()) {
            val u = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            val p = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            list.add(u to p)
        }

        cursor.close()
        return list
    }

}
