package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.ActionBar
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class BabyPage : AppCompatActivity() {




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.baby)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.parseColor("#ffffff") // #RRGGBB는 16진수 색상 코드입니다.
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

        val settingB = findViewById<ImageButton>(R.id.settingButton)

        settingB.setOnClickListener {
            val intent = Intent(this, SettingPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val babyName = "응애"
        val handler = Handler()

        var shitHour = 0
        var shitMin = 0
        val shitTime = findViewById<TextView>(R.id.shitTime)


        val shitCounter = object : Runnable {
            override fun run() {



                if (shitMin == 60)
                {
                    shitHour ++
                    shitMin = 0
                }
                shitTime.text = babyName + "(이)가 똥 싼지\n" + shitHour + "시간 " + shitMin + "분 지났어요!"
                shitMin++
                handler.postDelayed(this,60000)
            }
        }

        shitTime.setOnClickListener{
            shitHour = 0
            shitMin = 0
            shitTime.text = babyName + "(이)가 똥 싼지\n" + "0시간 " + "0분 지났어요!"
            handler.post(shitCounter)
        }


        var foodHour = 0
        var foodMin = 0
        val foodTime = findViewById<TextView>(R.id.foodTime)

        val foodCounter = object : Runnable {
            override fun run() {



                if (foodMin == 60)
                {
                    foodHour ++
                    foodMin = 0
                }
                foodTime.text = babyName + "(이)가 밥 먹은지\n" + foodHour + "시간 " + foodMin + "분 지났어요!"
                foodMin++
                handler.postDelayed(this,60000)
            }
        }

        foodTime.setOnClickListener{
            foodHour = 0
            foodMin = 0
            foodTime.text = babyName + "(이)가 밥 먹은지\n" + "0시간 " + "0분 지났어요!"
            handler.post(foodCounter)
        }

        var wakeHour = 0
        var wakeMin = 0
        val wakeTime = findViewById<TextView>(R.id.wakeTime)

        val wakeCounter = object : Runnable {
            override fun run() {



                if (wakeMin == 60)
                {
                    wakeHour ++
                    wakeMin = 0
                }
                wakeTime.text = babyName + "가 일어난지\n" + wakeHour + "시간 " + wakeMin + "분 지났어요!"
                wakeMin++
                handler.postDelayed(this,60000)
            }
        }

        wakeTime.setOnClickListener{
            wakeHour = 0
            wakeMin = 0
            wakeTime.text = babyName + "가 일어난지\n" + "0시간 " + "0분 지났어요!"
            handler.post(wakeCounter)
        }


        var plusHour = 0
        var plusMin = 0
        val plusTime = findViewById<Button>(R.id.plusTime)

        val plusCounter = object : Runnable {
            override fun run() {



                if (plusMin == 60)
                {
                    plusHour ++
                    plusMin = 0
                }
                plusTime.text = babyName + "가 +++\n" + plusHour + "시간 " + plusMin + "분 지났어요!"
                plusMin++
                handler.postDelayed(this,60000)
            }
        }

        plusTime.setOnClickListener{
            val intent = Intent(this, Vaccine::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }











     }
    }

