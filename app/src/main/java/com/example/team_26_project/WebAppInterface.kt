package com.example.team_26_project

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject

class WebAppInterface(private val mContext: Context) {
    // The @JavascriptInterface annotation makes this method callable from the HTML page.
    @JavascriptInterface
    fun getFlag(): String {
        return "PwnD{W3bVi3wz_Ar3_n0t_Alw4ys_s3cur3}"
    }


    @JavascriptInterface
    fun getUserObject(): String {
        val userObject = JSONObject()
        userObject.put("username", "Ahmed Reda")
            .put("age", 22)
            .put("email", "ahmedreda@home.eg")
            .put("phone number","01122334455")
        Log.i("WebAppInterface", "getUserObject: $userObject")
        return userObject.toString()
    }

    @JavascriptInterface
    fun showToast(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
    }
}