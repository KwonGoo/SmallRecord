package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Binder
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.text.Layout
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
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

        fun getCurrentTime(): String {
            val calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR_OF_DAY)
            var minute = calendar.get(Calendar.MINUTE)

            if (hour > 12){
                hour -= 12

                if(hour<10)
                    return "0$hour : $minute PM"
                else
                    return "$hour : $minute PM"
            }
            else {
                return "$hour : $minute AM"
            }

        }






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
            R.drawable.baby,R.drawable._1,R.drawable.diary
        )



        val name = arrayOf("똥싸기", "밥먹기", "잠자기")

        val buttonImage = intArrayOf(
            R.drawable.baseline_arrow_forward_ios_24
        )

        diaryArrayList = ArrayList()




        val doCountButton = findViewById<Button>(R.id.doButton)
        val doList = findViewById<ListView>(R.id.doList)

        doCountButton.setOnClickListener{
            if(doList.visibility == ListView.GONE)
            doList.visibility = ListView.VISIBLE

            else if(doList.visibility == ListView.VISIBLE){
            doList.visibility = ListView.GONE
        }
        }


        lateinit var itemList: MutableList<String>
        lateinit var adapter: ArrayAdapter<String>
        var i = 0



        itemList = mutableListOf("똥싸기", "밥먹기", "잠자기")

        // 어댑터 설정
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        doList.adapter = adapter

        doList.setOnItemClickListener { _, _, position, _ ->
            val time = arrayOf(getCurrentTime())
            val selectedTitle = itemList[position]

            when (selectedTitle) {
                "똥싸기" -> i=0
                "밥먹기" -> i=1
                "잠자기" -> i=2
            }

            val diaryItem = DiaryItem(time[0], imageId[i], name[i], buttonImage[0])
            diaryArrayList.add(diaryItem)
            binding.diaryList.adapter = DiaryAdapter(this, diaryArrayList)
            doList.visibility = ListView.GONE
        }



        }
    }

