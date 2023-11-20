package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView

class SettingPage : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.parseColor("#D9E8E3") // #RRGGBB는 16진수 색상 코드입니다.
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val foodB = findViewById<ImageButton>(R.id.foodButton)

        foodB.setOnClickListener {
            val intent = Intent(this, FoodPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val babyB = findViewById<ImageButton>(R.id.babyButton)

        babyB.setOnClickListener {
            val intent = Intent(this, BabyPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val diaryB = findViewById<ImageButton>(R.id.diaryButton)

        diaryB.setOnClickListener{
            val intent = Intent(this, DiaryPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val communityB = findViewById<ImageButton>(R.id.communityButton)

        communityB.setOnClickListener {
            val intent = Intent(this, CommunityPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val MyPage = findViewById<Button>(R.id.mypage)

        MyPage.setOnClickListener {
            val intent = Intent(this, Setting_MyPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val LoginPage = findViewById<Button>(R.id.loginpage)

        LoginPage.setOnClickListener {
            val intent = Intent(this, Setting_LoginPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val ASwitch = findViewById<Switch>(R.id.ASwitch)

        // SharedPreferences를 초기화
        sharedPreferences = getSharedPreferences("SwitchSettings", Context.MODE_PRIVATE)

        // 스위치의 초기 상태를 설정
        val switchState = sharedPreferences.getBoolean("switch_state", false)
        ASwitch.isChecked = switchState

        // 스위치의 상태가 변경될 때 SharedPreferences에 저장
        ASwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("switch_state", isChecked)
            editor.apply()
        }

        val appPreferences = AppPreferences(applicationContext)
        if (appPreferences.isLoggedIn()) {
            // 로그인된 상태
            val textText = findViewById<TextView>(R.id.babyname)

            LoginPage.visibility = View.GONE;
            MyPage.visibility = View.VISIBLE;
        } else {
            // 로그아웃된 상태
            LoginPage.visibility = View.VISIBLE;
            MyPage.visibility = View.GONE;
        }

    }
}

