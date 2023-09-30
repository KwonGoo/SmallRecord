package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
        loginButton.setOnClickListener {
            val userName = loginId.text.toString()
            val userPwd = loginPassword.text.toString()

            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    val loginButton = findViewById<Button>(R.id.loginpage)
                    val myButton = findViewById<Button>(R.id.mypage)
                    if (success) {
                        val userEmail = jsonObject.getString("userEmail")
                        val userPwd = jsonObject.getString("userPassword")
                        val userName = jsonObject.getString("userName")

                        Toast.makeText(
                            applicationContext,
                            "${userName}님 환영합니다.",
                            Toast.LENGTH_SHORT
                        ).show()

                        loginButton.visibility = View.INVISIBLE
                        myButton.visibility = View.VISIBLE

                        val intent = Intent(this@Setting_LoginPage, SettingPage::class.java)

                        intent.putExtra("userEmail", userEmail)
                        intent.putExtra("userPassword", userPwd)
                        intent.putExtra("userName", userName)

                        startActivity(intent)

                    } else {
                        Toast.makeText(applicationContext, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            val loginRequest = Setting_LoginRequest(userName, userPwd, responseListener)
            val queue = Volley.newRequestQueue(this@Setting_LoginPage)
            queue.add(loginRequest)
        }
    }

    override fun onStop() {
        super.onStop()
        dialog?.dismiss()
        dialog = null
    }
}