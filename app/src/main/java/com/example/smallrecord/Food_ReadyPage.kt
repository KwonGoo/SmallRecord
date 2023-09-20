package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class Food_ReadyPage :AppCompatActivity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.readymade)

        val actionBar: ActionBar? = supportActionBar

        if (actionBar != null) {
            actionBar.hide()
        }

        val BackFoodB = findViewById<Button>(R.id.BackFoodButton)
        BackFoodB.setOnClickListener {
            val intent = Intent(this,FoodPage::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }
    }
}