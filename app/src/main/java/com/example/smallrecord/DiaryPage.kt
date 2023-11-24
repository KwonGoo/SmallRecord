package com.example.smallrecord

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Binder
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
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
            private val webSocketManager = WebSocketManager()
            var i = 0;

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
            R.drawable.diaper,R.drawable.babyfood,R.drawable.sleeping
        )



        val name = arrayOf("똥싸기", "밥먹기", "잠자기")

        val buttonImage = intArrayOf(
            R.drawable.arrow
        )

        diaryArrayList = ArrayList()




        val doCountButton = findViewById<Button>(R.id.doButton)
        val doList = findViewById<ListView>(R.id.doList)

        doCountButton.setOnClickListener{
            val dialog = Dialog(this, R.style.TransparentDialogStyle)
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_writediary, null)
            dialog.setContentView(dialogView)

            val plus = dialogView.findViewById<ImageButton>(R.id.save)
            val seletefood = dialogView.findViewById<ImageButton>(R.id.seletefood)
            val selectdiaper = dialogView.findViewById<ImageButton>(R.id.selectdiaper)
            val selectsleep = dialogView.findViewById<ImageButton>(R.id.selectsleep)

            selectdiaper.setOnClickListener {
                i = 0
                selectdiaper.setBackgroundColor(Color.parseColor("#FFFCAD83"))
                seletefood.setBackgroundColor(Color.parseColor("#FEFEFE"))
                selectsleep.setBackgroundColor(Color.parseColor("#FEFEFE"))
            }

            seletefood.setOnClickListener {
                i = 1
                seletefood.setBackgroundColor(Color.parseColor("#FFFCAD83"))
                selectdiaper.setBackgroundColor(Color.parseColor("#FEFEFE"))
                selectsleep.setBackgroundColor(Color.parseColor("#FEFEFE"))
            }

            selectsleep.setOnClickListener {
                i = 2
                selectsleep.setBackgroundColor(Color.parseColor("#FFFCAD83"))
                seletefood.setBackgroundColor(Color.parseColor("#FEFEFE"))
                selectdiaper.setBackgroundColor(Color.parseColor("#FEFEFE"))
            }

            plus.setOnClickListener {
                val time = arrayOf(getCurrentTime())
                val id = diaryArrayList.size.toLong()

                val diaryItem = DiaryItem(id, time[0], imageId[i], name[i], buttonImage[0])
                diaryArrayList.add(diaryItem)
                binding.diaryList.adapter = DiaryAdapter(this, diaryArrayList)
                //doList.visibility = ListView.GONE
                dialog.dismiss() // 다이얼로그 닫기
            }

            dialog.show()

            /*if(doList.visibility == ListView.GONE)
            doList.visibility = ListView.VISIBLE

            else if(doList.visibility == ListView.VISIBLE){
            doList.visibility = ListView.GONE
        }*/
        }


        lateinit var itemList: MutableList<String>
        lateinit var adapter: ArrayAdapter<String>
        var i = 0



        itemList = mutableListOf("똥싸기", "밥먹기", "잠자기")

        // 어댑터 설정
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        doList.adapter = adapter



        }
    }

