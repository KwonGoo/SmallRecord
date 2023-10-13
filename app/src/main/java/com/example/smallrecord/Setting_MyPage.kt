package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton

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

        val appPreferences = AppPreferences(applicationContext)
        val Logout = findViewById<Button>(R.id.logout)
        Logout.setOnClickListener{
            appPreferences.clearUserCredentials()
            val intent = Intent(this, SettingPage::class.java)
            startActivity(intent)
        }
    }
}