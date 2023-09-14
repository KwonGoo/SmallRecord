package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.widget.Button
import android.widget.ImageButton
import android.widget.ArrayAdapter
import android.widget.ListView

class CommunityPage : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
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



        val settingB = findViewById<ImageButton>(R.id.settingButton)

        settingB.setOnClickListener {
            val intent = Intent(this, SettingPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)

        }


        val listView = findViewById<ListView>(R.id.listView)


        val itemList = listOf(
            "게시물 1: 제목 1",
            "게시물 2: 제목 2",
            "게시물 3: 제목 3",
            "게시물 4: 제목 4",
            "게시물 5: 제목 5"
        )

        // 어댑터 설정
        val adapter = ArrayAdapter(this, R.layout.community, R.id.textViewTitle, itemList)
        listView.adapter = adapter


        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = itemList[position]
        }
    }
}

