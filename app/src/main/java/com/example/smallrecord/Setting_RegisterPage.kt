package com.example.smallrecord

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class Setting_RegisterPage : AppCompatActivity() {

    private lateinit var joinEmail: EditText
    private lateinit var joinPassword: EditText
    private lateinit var joinName: EditText
    private lateinit var joinPwck: EditText
    private lateinit var joinButton: Button
    private lateinit var checkButton: Button
    private lateinit var previousButton: Button
    private lateinit var joindatebtn : ImageButton
    private lateinit var datetext : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_join)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        joinEmail = findViewById(R.id.join_email)
        joinPassword = findViewById(R.id.join_password)
        joinName = findViewById(R.id.join_id)
        joinPwck = findViewById(R.id.join_pwck)
        checkButton = findViewById(R.id.check_button)
        joinButton = findViewById(R.id.join_button)

        joindatebtn = findViewById(R.id.joindatebtn)
        datetext = findViewById(R.id.join_date)
        val datePickerHandler = DatePickerHandler(this, datetext)
        joindatebtn.setOnClickListener {
            datePickerHandler.showDatePickerDialog()
        }


        previousButton = findViewById(R.id.delete) // 뒤로가기 버튼
        previousButton.setOnClickListener {
            finish()
        }
    }
}