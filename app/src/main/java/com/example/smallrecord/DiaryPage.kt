package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.text.Layout
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import com.example.smallrecord.databinding.DiaryBinding


class DiaryPage : AppCompatActivity() {
            private lateinit var binding : DiaryBinding
            private lateinit var diaryArrayList: ArrayList<DiaryItem>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DiaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




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

        val babyB = findViewById<ImageButton>(R.id.babyButton)

        babyB.setOnClickListener {
            val intent = Intent(this, BabyPage::class.java)
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

        val imageId = intArrayOf(
            R.drawable.baby,R.drawable.diary
        )

        val time = arrayOf("10 : 02 AM","12 : 03 AM")

        val name = arrayOf("이유식", "똥싸기")

        val buttonImage = intArrayOf(
            R.drawable.baseline_arrow_forward_ios_24,R.drawable.baseline_arrow_forward_ios_24
        )

        diaryArrayList = ArrayList()

        for (i in name.indices){
            val diaryItem = DiaryItem(time[i], imageId[i], name[i], buttonImage[i])
            diaryArrayList.add(diaryItem)
        }

        binding.diaryList.adapter = DiaryAdapter(this, diaryArrayList)



        }
    }

