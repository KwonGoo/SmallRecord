package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request

class Setting_MyPage : AppCompatActivity() {



    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_mypage)

        val mainName = findViewById<TextView>(R.id.mainName)
        val accessToken = intent.getStringExtra("accessToken")

        println("이거 진짜 $accessToken")
if(accessToken != null) {
    val babyInfoJson = JSONObject(accessToken)
    val babyRealInfo = babyInfoJson.getJSONObject("data")

    println(babyRealInfo.getString("name"))

    mainName.text = babyRealInfo.getString("name")
}
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val turn = findViewById<ImageButton>(R.id.turn_setting)

        turn.setOnClickListener {
            val intent = Intent(this, SettingPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val appPreferences = AppPreferences(this)

        val Logout = findViewById<Button>(R.id.logout)
        Logout.setOnClickListener{
            appPreferences.clearUserCredentials()
            val intent = Intent(this, SettingPage::class.java)
            startActivity(intent)
        }


    }


}