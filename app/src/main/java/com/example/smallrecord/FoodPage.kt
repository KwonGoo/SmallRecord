package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.smallrecord.foodreadypage.Food_ReadyPage

class FoodPage : AppCompatActivity() {
    private lateinit var myList: MutableList<String>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: ArrayAdapter<String>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val babyB = findViewById<ImageButton>(R.id.babyButton)

        babyB.setOnClickListener {
            val intent = Intent(this, BabyPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val diaryB = findViewById<ImageButton>(R.id.diaryButton)

        diaryB.setOnClickListener {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.parseColor("#8bcbc8") // #RRGGBB는 16진수 색상 코드입니다.
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val foodPlusB = findViewById<Button>(R.id.food_plusButton)
        val homemadeB = findViewById<Button>(R.id.homemadeButton)
        val readyMadeB = findViewById<Button>(R.id.readymadeButton)

        foodPlusB.setOnClickListener {
            foodPlusB.visibility = View.GONE
            homemadeB.visibility = View.VISIBLE
            readyMadeB.visibility = View.VISIBLE
        }
        homemadeB.setOnClickListener {
            val intent = Intent(this, Food_HomePage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            foodPlusB.visibility = View.VISIBLE
            homemadeB.visibility = View.GONE
            readyMadeB.visibility = View.GONE
        }
        readyMadeB.setOnClickListener {
            val intent = Intent(this, Food_ReadyPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            foodPlusB.visibility = View.VISIBLE
            homemadeB.visibility = View.GONE
            readyMadeB.visibility = View.GONE
        }

        val removeLayout = findViewById<View>(R.id.Removelayout)

        removeLayout.setOnClickListener{
            foodPlusB.visibility = View.VISIBLE
            homemadeB.visibility = View.GONE
            readyMadeB.visibility = View.GONE
        }

        val foodMylistV = findViewById<ListView>(R.id.myFoodListV)

        // myList와 sharedPreferences 초기화
        myList = mutableListOf()
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        // SharedPreferences에서 저장된 목록 로드
        myList.addAll(sharedPreferences.getStringSet("myList", emptySet()) ?: emptySet())

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList)
        foodMylistV.adapter = adapter

        // 리스트뷰에서 항목을 롱클릭하여 삭제
        foodMylistV.setOnItemLongClickListener { _, _, position, _ ->
            val itemToRemove = myList[position]
            showDeleteDialog(itemToRemove)
            true
        }

        if (intent.hasExtra("productName")) {
            val productName = intent.getStringExtra("productName")
            if (productName != null) {
                myList.add(productName)
                updateMyListInSharedPreferences()
                adapter.notifyDataSetChanged()
            }
        }
    }
    // 항목을 삭제하는 컨텍스트 메뉴 보여주기
    private fun showDeleteDialog(itemToRemove: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("삭제")
        alertDialog.setMessage("이 항목을 삭제하시겠습니까?")
        alertDialog.setPositiveButton("예") { _, _ ->
            // 항목 삭제
            myList.remove(itemToRemove)
            updateMyListInSharedPreferences()
            adapter.notifyDataSetChanged()
        }
        alertDialog.setNegativeButton("아니요") { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }

    // myList를 업데이트하고 SharedPreferences에 저장
    private fun updateMyListInSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putStringSet("myList", myList.toSet())
        editor.apply()
    }
}