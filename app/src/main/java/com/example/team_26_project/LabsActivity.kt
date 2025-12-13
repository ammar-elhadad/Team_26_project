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

        //module 1
        val module1Card: View = findViewById(R.id.module1)
        val title1: TextView = module1Card.findViewById(R.id.tvVulnTitle)
        title1.text = "Intro to Android Security"
        val read1: Button = module1Card.findViewById(R.id.btnReadLab)
        read1.setOnClickListener {
            val obad = Intent(this, intro_to_android_security::class.java)
            startActivity(obad)

        }


        //module sql
        val moduleSqlCard: View = findViewById(R.id.module_SQL)

        val sqlTitle: TextView = moduleSqlCard.findViewById(R.id.tvVulnTitle)
        sqlTitle.text = "SQL Injection Bypass"

        val sqlButton: Button = moduleSqlCard.findViewById(R.id.btnReadLab)
        sqlButton.setOnClickListener {
            startActivity(Intent(this, SQL_injection::class.java))
        }


        //module2
        val intent_module:View = findViewById(R.id.module2)
        val intent_title: TextView = intent_module.findViewById(R.id.tvVulnTitle)
        intent_title.text = "Intent Activity"
        val intent_lab_button : Button = intent_module.findViewById(R.id.btnReadLab)
        intent_lab_button.setOnClickListener {
            val intent_lab = Intent(this, IntentLab::class.java)
            startActivity(intent_lab)
        }

        //module 3
        val redirect_module:View = findViewById(R.id.module3)
        val redirect_title: TextView = redirect_module.findViewById(R.id.tvVulnTitle)
        redirect_title.text = "Intent Redirection"
        val redirect_lab_button : Button = redirect_module.findViewById(R.id.btnReadLab)
        redirect_lab_button.setOnClickListener {
            val redirect_intent = Intent(this, redirect_activity::class.java)
            startActivity(redirect_intent)
        }

        //module 4
        val deep_link_module:View = findViewById(R.id.module4)
        val deep_link_title : TextView = deep_link_module.findViewById(R.id.tvVulnTitle)
        deep_link_title.text = "Deep Link"
        val deep_link_lab_button : Button = deep_link_module.findViewById(R.id.btnReadLab)
        deep_link_lab_button.setOnClickListener {
            val deep_link_intent = Intent(this, deepLink::class.java)
            startActivity(deep_link_intent)
        }



        // Insufficient Cryptography
        val cryptoModule: View = findViewById(R.id.crypto)
        val cryptoTitle: TextView = cryptoModule.findViewById(R.id.tvVulnTitle)
        cryptoTitle.text = "Insufficient Cryptography"

        val CryptoButton = cryptoModule.findViewById<Button>(R.id.btnReadLab)
        CryptoButton.setOnClickListener {
            val intent = Intent(this, InsufficientCryptography::class.java)
            startActivity(intent)
        }



        // Insecure WebView
        val webviewModule: View = findViewById(R.id.webview)
        val webviewTitle: TextView = webviewModule.findViewById(R.id.tvVulnTitle)
        webviewTitle.text = "Insecure WebView Activity"

        val webviewButton = webviewModule.findViewById<Button>(R.id.btnReadLab)
        webviewButton.setOnClickListener {
            val intent = Intent(this, InsecureWebView::class.java)
            startActivity(intent)
        }
    }
}