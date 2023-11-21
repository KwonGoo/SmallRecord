package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class Vaccine : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vaccine)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        val babyback = findViewById<ImageButton>(R.id.babyback)
        babyback.setOnClickListener {
            val intent = Intent(this, BabyPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}