package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.parseColor("#8bcbc8") // #RRGGBB는 16진수 색상 코드입니다.
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val BackFoodB = findViewById<Button>(R.id.BackFoodButton)
        BackFoodB.setOnClickListener {
            val intent = Intent(this,FoodPage::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }

        val madeButton = findViewById<Button>(R.id.readymadepicB)
        madeButton.setOnClickListener{
            var intent = Intent(this,BarcodeCameraPage::class.java)
            startActivity(intent)
        }
    }
}