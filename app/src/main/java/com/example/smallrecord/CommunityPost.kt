package com.example.smallrecord

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class CommunityPost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_post) // CommunityPost 액티비티의 레이아웃 파일 설정

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val contentTextView = findViewById<TextView>(R.id.contentTextView)
        val backButton = findViewById<Button>(R.id.backButton)

        val intent = intent
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        titleTextView.text = title
        contentTextView.text = content

        backButton.setOnClickListener {
            finish()
        }
    }
}