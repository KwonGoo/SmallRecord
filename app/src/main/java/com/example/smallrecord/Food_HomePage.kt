package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch

class Food_HomePage : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homemade)

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
