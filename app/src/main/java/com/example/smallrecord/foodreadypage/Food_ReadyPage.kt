package com.example.smallrecord.foodreadypage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smallrecord.BarcodeCameraPage
import com.example.smallrecord.FoodPage
import com.example.smallrecord.R
import com.example.smallrecord.entity.FoodEntity
import com.example.smallrecord.entity.FoodEntitySampleData
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import org.json.JSONObject

class Food_ReadyPage : AppCompatActivity() {


    companion object {
        const val REQUEST_CODE_BARCODE = 1
    }

    private lateinit var foodNameSearchedRecyclerView: RecyclerView// = findViewById<ListView>(R.id.foodNameSearchedRecyclerView)
    private lateinit var foodNameSearchedAdapter: FoodNameSearchedAdapter// = FoodNameSearchedAdapter(this)



            @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.readymade)

                val foodInfo = intent.getStringExtra("foodInfo")
                if(foodInfo != null) {
                    val foodNameTextView: TextView = findViewById(R.id.foodname)
                    val madeNameTextView: TextView = findViewById(R.id.madecompany)
                    val carbohydrateTextView: TextView = findViewById(R.id.Carbohydrate)
                    val proteinTextView: TextView = findViewById(R.id.Protein)
                    val fatTextView: TextView = findViewById(R.id.Fat)
                    val sodiumTextView: TextView = findViewById(R.id.Sodium)
                    val cholesterolTextView: TextView = findViewById(R.id.Cholesterol)
                    val sugarsTextView: TextView = findViewById(R.id.Sugars)

                    val foodInfoJson = JSONObject(foodInfo)

                    foodNameTextView.text = foodInfoJson.getString("product_name")
                    madeNameTextView.text = foodInfoJson.getString("manufacturer")
                    carbohydrateTextView.text = foodInfoJson.getString("carbohydarte_g")
                    proteinTextView.text = foodInfoJson.getString("protein_g")
                    fatTextView.text = foodInfoJson.getString("fat_g")
                    sodiumTextView.text = foodInfoJson.getString("sodium_mg")
                    cholesterolTextView.text = foodInfoJson.getString("cholesterol_mg")
                    sugarsTextView.text = foodInfoJson.getString("total_sugars_g")
                }


                var barCodeNum = ""
        val actionBar: ActionBar? = supportActionBar

        fun getBarcodeNum(barcodeCameraPage: BarcodeCameraPage): String {
            val barcodeNumData = barcodeCameraPage.lastText
            return barcodeNumData!!
        }

        if (actionBar != null) {
            actionBar.hide()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                android.graphics.Color.parseColor("#8bcbc8") // #RRGGBB는 16진수 색상 코드입니다.
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val BackFoodB = findViewById<ImageButton>(R.id.BackFoodButton)
        BackFoodB.setOnClickListener {
            val intent = Intent(this, FoodPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }


        val madeButton = findViewById<Button>(R.id.readymadepicB)
        madeButton.setOnClickListener {
            var intent = Intent(this, BarcodeCameraPage::class.java)
            startActivity(intent)

        }

        foodNameSearchedRecyclerView = findViewById(R.id.foodNameSearchedRecyclerView)
        foodNameSearchedRecyclerView.layoutManager = LinearLayoutManager(this)
        foodNameSearchedAdapter = FoodNameSearchedAdapter(this, onClickListener)
        foodNameSearchedRecyclerView.adapter = foodNameSearchedAdapter

        val readymadeSearchEt = findViewById<EditText>(R.id.readymadeSearchEt)

        readymadeSearchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {reqFoodNameList(s.toString())}
        })

        /*
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
         */


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_BARCODE && resultCode == RESULT_OK) {
            // 여기서 데이터를 가져와서 사용하면 됩니다.
            val foodInfoString = data?.getStringExtra("foodInfo")

            // foodInfoString을 원하는 형식으로 파싱하거나 사용합니다.
            // 예: JSON 형식의 문자열을 JSONObject로 변환
            val foodInfo = JSONObject(foodInfoString)
            println("11111111 $foodInfo")

            // 이제 foodInfo를 사용할 수 있습니다.
        }
    }


    private fun reqFoodNameList(userSearchFoodName: String) {
        println("afterTextChanged userSearchFoodName : $userSearchFoodName")

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000L)
            // Network 요청
            // HttpUrlConnection
            // Retrofit
            // Ktor

            val foodEntityList: List<FoodEntity> = FoodEntitySampleData
                .dataList
                .filter { food -> food.productName.contains(userSearchFoodName) }
            // select * from AAA where product
            // server return List<FoodDto>
            // List<FoodDto> -> List<FoodEntity> data mapping // ObjectMapper, Gson, MapStructure

            withContext(Dispatchers.Main) {
                reqFoodNameList(foodEntityList)
            }
        }
    }

    private fun reqFoodNameList(foodEntityList: List<FoodEntity>) {
        foodEntityList.forEach { println("reqFoodNameList : " + it) }

        if (foodEntityList.isEmpty()) return
        foodNameSearchedAdapter.setItems(foodEntityList)
        foodNameSearchedRecyclerView.visibility = View.VISIBLE
    }

    private val onClickListener = View.OnClickListener { view ->
        val foodEntity = view.tag as FoodEntity?
        println("onClickListener : " + foodEntity)
//        foodEntity.
    }






}