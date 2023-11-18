package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Setting_LoginPage : AppCompatActivity() {

    private var dialog: AlertDialog? = null
    private lateinit var loginId: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var joinButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_login)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        loginId = findViewById(R.id.login_id)
        loginPassword = findViewById(R.id.login_password)

        joinButton = findViewById(R.id.join_page)
        joinButton.setOnClickListener {
            val intent = Intent(this@Setting_LoginPage, Setting_RegisterPage::class.java)
            startActivity(intent)
        }

        loginButton = findViewById(R.id.login_button)

    }
}