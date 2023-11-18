package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
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

        val loginId:String ?= ""
        val password:String ?= ""
        val name:String ?= ""
         val birthDate:String ?= ""
         val gender:String ?= ""
        val email:String ?= ""
        val signupButton = findViewById<Button>(R.id.join_button)

        //loginId = findViewById(R.id.)

        signupButton.setOnClickListener{
            val post = "POST /api/member/signup"

            val sendMessage = buildJsonObject {
                put("loginId",loginId)
                put("password",password)
                put("name",name)
                put("birthDate",birthDate)
                put("gender",gender)
                put("email",email)
            }

            webSocketManager.sendPostToServer(post, sendMessage)

        }



    }
}