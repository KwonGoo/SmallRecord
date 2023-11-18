package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.json.JSONException
import org.json.JSONObject
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class Setting_RegisterPage : AppCompatActivity() {



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_join)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        val webSocketManager = WebSocketManager()

        val loginIdText = findViewById<EditText>(R.id.join_id)
        val passwordText = findViewById<EditText>(R.id.join_password)
        val nameText = findViewById<EditText>(R.id.join_name)
        val birthText = findViewById<EditText>(R.id.join_date)
        val genderText = findViewById<RadioGroup>(R.id.gender)
        val emailText = findViewById<EditText>(R.id.join_email)

        val signupButton = findViewById<Button>(R.id.join_button)




        var gender = ""


        genderText.setOnCheckedChangeListener { group, checkedId ->
            // checkedId는 현재 선택된 라디오 버튼의 ID입니다.
            when (checkedId) {
                R.id.man -> {
                    gender = "MAN"
                }

                R.id.woman -> {
                    gender = "WOMAN"
                }
            }
        }



        signupButton.setOnClickListener{
            val post = "POST /api/member/signup"
            val loginId = loginIdText.text.toString()
            val password:String = passwordText.text.toString()
            val name:String = nameText.text.toString()
            val birthDate:String = birthText.text.toString()
            val email:String = emailText.text.toString()


            val sendMessage = buildJsonObject {
                put("loginId",loginId)
                put("password",password)
                put("name",name)
                put("birthDate",birthDate)
                put("gender",gender)
                put("email",email)
            }

            webSocketManager.sendPostToServer(post, sendMessage)



            val intent = Intent(this, Setting_LoginPage::class.java)



            startActivity(intent)

        }



    }
}