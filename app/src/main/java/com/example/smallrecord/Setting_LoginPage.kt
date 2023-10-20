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
        loginButton.setOnClickListener {
            val userEmail = loginId.text.toString()
            val userPassword = loginPassword.text.toString()

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                // 이메일 또는 비밀번호 필드 중 하나라도 비웠을 때
                val builder = AlertDialog.Builder(this@Setting_LoginPage)
                builder.setMessage("모두 입력해주세요")
                    .setNegativeButton("확인", null)
                    .create()
                    .show()
            }

            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")

                    if (success) {
                        val userEmail = jsonObject.getString("userEmail")
                        val userPassword = jsonObject.getString("userPassword")
                        val userName = jsonObject.getString("userName")

                        val appPreferences = AppPreferences(applicationContext)
                        appPreferences.saveUserCredentials(userEmail, userName)

                        Toast.makeText(
                            applicationContext,
                            "${userName}님 환영합니다.",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@Setting_LoginPage, SettingPage::class.java)

                        intent.putExtra("userEmail", userEmail)
                        intent.putExtra("userPassword", userPassword)
                        intent.putExtra("userName", userName)

                        startActivity(intent)

                    } else {
                        Toast.makeText(applicationContext, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            val loginRequest = Setting_LoginRequest(userEmail, userPassword, responseListener)
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