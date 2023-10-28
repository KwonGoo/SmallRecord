package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*


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
        data class FoodInfo(val name: String, val description: String, val Carbohydrates: String, val Protein: String, val Fat: String, val Sodium: String, val Cholesterol: String, val Sugar: String)
        val readysearchingV = findViewById<SearchView>(R.id.readymadeSearch)
        val readysearchingL = findViewById<ListView>(R.id.searchingListV)
        val infoTextView = findViewById<TextView>(R.id.readyInfo)
        // 어댑터 생성
        val dataList = listOf(  "영기엄마의 소고기 미역 이유식",
            "승민엄마의 단호박 깔라만시 이유식",
            "쏘영마미의 붉닭 치즈 떡뽂이",
            "병거니와 통처니의 사과 떡볶이")
        val infoList = mutableListOf<FoodInfo>()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList)
        // 검색뷰 활성화
        readysearchingV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    readysearchingL.visibility = View.VISIBLE
                    // 여기에서 newText를 기반으로 검색 결과 데이터를 업데이트하고 어댑터를 갱신합니다

                    adapter.clear()
                    infoList.clear()
                    val filteredResults = dataList.filter { it.contains(newText.orEmpty()) }
                    adapter.addAll(filteredResults)
                    adapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    // 예외 처리 코드
                    Log.e("TAG", "검색 결과 업데이트 중 오류 발생", e)
                }
                return true
            }
        })

        readysearchingL.adapter = adapter
        readysearchingL.setOnItemClickListener { parent, view, position, id ->

            // 사용자가 첫 번째 리스트뷰에서 항목을 클릭할 때 실행됨
            val selectedItem = dataList[position]

            // 선택한 항목에 대한 정보를 가져와서 두 번째 리스트뷰에 추가
            val selectedFoodInfo = when (selectedItem) {
                "영기엄마의 소고기 미역 이유식" -> {
                    "제품명 :\n영기엄마의 소고기 미역 이유식\n"+
                    "제조사 : 영기맘스\n"+
                    "탄수화물 : 10g\n"+
                    "단백질 : 2g\n"+
                    "지방 : 1g\n"+
                    "나트륨 : 11mg\n"+
                    "콜레스테롤 : 0g\n"+
                    "당류 : 1g\n"
                }

                "승민엄마의 단호박 깔라만시 이유식" -> {
                        "제품명 :\n승민엄마의 단호박 깔라만시 이유식\n"+
                        "제조사 : 아따맘마\n"+
                        "탄수화물 : 12g\n"+
                        "단백질 : 4g\n"+
                        "지방 : 2g\n"+
                        "나트륨 : 15mg\n"+
                        "콜레스테롤 : 0g\n"+
                        "당류 : 2g"
                }

                "쏘영마미의 붉닭 치즈 떡뽂이" -> {
                        "제품명 :\n쏘영마미의 붉닭 치즈 떡볶이\n"+
                        "제조사 : 금병영\n"+
                        "탄수화물 : 400g\n"+
                        "단백질 : 12g\n"+
                        "지방 : 24g\n"+
                        "나트륨 : 300mg\n"+
                        "콜레스테롤 : 0g\n"+
                        "당류 : 4g"
                }

                "병거니와 통처니의 사과 떡볶이" -> {
                        "제품명 :\n병거니와 통처니의 사과 떡볶이\n"+
                        "제조사 : 금병영\n"+
                        "탄수화물 : 300g\n"+
                        "단백질 : 13g\n"+
                        "지방 : 12g\n"+
                        "나트륨 : 400mg\n"+
                        "콜레스테롤 : 0g\n"+
                        "당류 : 40g"
                }

                else -> ""
            }
            if (selectedFoodInfo != "") {
                infoTextView.text = selectedFoodInfo
            }
            readysearchingV.setQuery("", false)
            readysearchingV.isIconified = true
            readysearchingL.visibility = View.GONE
        }
        val readyeditB = findViewById<Button>(R.id.readyeditB)
        readyeditB.setOnClickListener {
            val intent = Intent(this, FoodPage::class.java)
            val selectedData = infoTextView.text.toString()

            // "제품명" 정보 추출
            val productNameMatch = Regex("제품명 :\\s*([^\\n]+)").find(selectedData)
            val productName = productNameMatch?.groups?.get(1)?.value

            // Intent에 제품명 추가
            intent.putExtra("productName", productName)

            startActivity(intent)
        }
    }
}