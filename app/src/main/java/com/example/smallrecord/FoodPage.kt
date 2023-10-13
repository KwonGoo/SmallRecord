package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class FoodPage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val babyB = findViewById<ImageButton>(R.id.babyButton)

        babyB.setOnClickListener {
            val intent = Intent(this, BabyPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val diaryB = findViewById<ImageButton>(R.id.diaryButton)

        diaryB.setOnClickListener {
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

        val settingB = findViewById<ImageButton>(R.id.settingButton)

        settingB.setOnClickListener {
            val intent = Intent(this, SettingPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.parseColor("#8bcbc8") // #RRGGBB는 16진수 색상 코드입니다.
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val foodPlusB = findViewById<Button>(R.id.food_plusButton)
        val homemadeB = findViewById<Button>(R.id.homemadeButton)
        val readyMadeB = findViewById<Button>(R.id.readymadeButton)

        foodPlusB.setOnClickListener {
            foodPlusB.visibility = View.GONE
            homemadeB.visibility = View.VISIBLE
            readyMadeB.visibility = View.VISIBLE
        }
        homemadeB.setOnClickListener {
            val intent = Intent(this, Food_HomePage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            foodPlusB.visibility = View.VISIBLE
            homemadeB.visibility = View.GONE
            readyMadeB.visibility = View.GONE
        }
        readyMadeB.setOnClickListener {
            val intent = Intent(this,Food_ReadyPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            foodPlusB.visibility = View.VISIBLE
            homemadeB.visibility = View.GONE
            readyMadeB.visibility = View.GONE
        }

        val removeLayout = findViewById<View>(R.id.Removelayout)

        removeLayout.setOnClickListener{
            foodPlusB.visibility = View.VISIBLE
            homemadeB.visibility = View.GONE
            readyMadeB.visibility = View.GONE
        }
    }
}

