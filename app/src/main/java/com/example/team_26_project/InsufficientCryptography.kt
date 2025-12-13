package com.example.team_26_project

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.*
import org.json.JSONObject
import java.io.IOException



class InsufficientCryptography : AppCompatActivity() {
    private val BASE_URL = "http://10.0.2.2:4444"
    private var token: String? = ""
    private val client = OkHttpClient()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insufficient_cryptography)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameText: EditText = findViewById(R.id.etUsername)
        val tvToken: TextView = findViewById(R.id.tvToken)
        val tvResult: TextView = findViewById(R.id.tvResult)

        // the three buttons for interacting with the flask apis
        val btnGet: Button = findViewById(R.id.btnGetTicket)
        val btnTamper: Button = findViewById(R.id.btnTamper)
        val btnValidate: Button = findViewById(R.id.btnValidate)


        btnGet.setOnClickListener {
            val username = usernameText.text.toString()
            if (username.isEmpty()) {
                Toast.makeText(this, "Enter username to get a Ticker", Toast.LENGTH_SHORT).show()
            }

            GetTicket(username){ runOnUiThread { tvToken.text = it; token = it } } //gets the ticket from the api and then print it on screen
        }


        btnTamper.setOnClickListener {
            if (token.isNullOrEmpty()) {
                Toast.makeText(this, "Get a ticket first", Toast.LENGTH_SHORT).show()
            }
            val forgedToken = TamperToken(token!!) // this button might be pressed and when token = null `ii` is needed
            if (forgedToken == "") {
                Toast.makeText(this, "Invalid token", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            token = forgedToken
            tvToken.text = token
            tvResult.text = "STATUS: BIT FLIPPED!"
            tvResult.setTextColor(0xFFD50000.toInt())
        }

        btnValidate.setOnClickListener {
            if (!token.isNullOrEmpty()) {
                VipCheck(token!!) { response , success ->
                    runOnUiThread {
                        tvResult.text = response
                        if (success){
                            tvResult.setTextColor(0xFF39FF14.toInt())
                        } else {
                            tvResult.setTextColor(0xFFD50000.toInt())
                        }
                    }
                }
            }
        }

    }





    private fun GetTicket(username: String, callback: (String) -> Unit) {
        val formData = FormBody.Builder().add("username",username).add("age","22").build()
        val request = Request.Builder().url("$BASE_URL/Get-ticket").post(formData).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread { Toast.makeText(this@InsufficientCryptography, "Connection Failed", Toast.LENGTH_LONG).show() }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonStr = response.body?.string()
                    val json = JSONObject(jsonStr ?: "{}")
                    if (json.has("Ticket-Code")) {
                        callback(json.getString("Ticket-Code"))    // the token we need to tamper with
                    }
                }
            }
        })
    }


    private fun TamperToken(ticket: String): String{
        Log.i("TamperToken", "ticket: $ticket")
        if (ticket.length < 32) return ""

        val BLOCK_SIZE = 32
        var tickethex = ticket.toCharArray()
        val lastChar = ticket[BLOCK_SIZE-1]
        val newChar = (lastChar.code - 1).toChar()

        tickethex[BLOCK_SIZE-1] = newChar

        Log.i("TamperToken", "Tampere ticket: ${tickethex}")
        return String(tickethex)
    }


    private fun VipCheck(ticket: String, callback: (String, Boolean) -> Unit) {
    val formData = FormBody.Builder().add("Ticket-code",ticket).build()
    val request = Request.Builder().url("$BASE_URL/Vip-check").post(formData).build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            runOnUiThread { Toast.makeText(this@InsufficientCryptography, "Connection Failed", Toast.LENGTH_LONG).show() }
        }
        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string() ?: ""
            try {
                val json = JSONObject(body)
                if (json.has("flag")) {
                    callback("ACCESS GRANTED\n" + json.getString("flag"), true)
                } else if (json.has("error")) {
                    callback("ACCESS DENIED\n" + json.getString("error"), false)
                }
            } catch (e: Exception) {
                callback("Parsing Error (Bad Decryption)", false)
            }
        }
    })

    }


}



