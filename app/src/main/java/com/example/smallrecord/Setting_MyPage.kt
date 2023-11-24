package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class Setting_MyPage : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_mypage)

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

        val appPreferences = AppPreferences(this, WebSocketManager())
        val textName = findViewById<TextView>(R.id.mainName)
        val textBirth = findViewById<TextView>(R.id.birthtxt)
        val changeName = appPreferences.getName()
        val changeBirth = appPreferences.getBirthDate()

        if (appPreferences.isLoggedIn()){
            textName.text = "$changeName"
            textBirth.text = "$changeBirth 애 태어나 건강하게 자랐어요!"
        }

        val Logout = findViewById<Button>(R.id.logout)
        Logout.setOnClickListener{
            appPreferences.clearUserCredentials()
            val intent = Intent(this, SettingPage::class.java)
            startActivity(intent)
        }
    }
}