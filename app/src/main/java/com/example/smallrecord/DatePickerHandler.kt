package com.example.smallrecord

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import java.util.*

class DatePickerHandler(private val context: Context, private val editText: EditText) {

    fun showDatePickerDialog() {
        val currentDate = editText.text.toString()
        val calendar = Calendar.getInstance()

        if (currentDate.isNotEmpty()) {
            val dateParts = currentDate.split("-")
            val year = dateParts[0].toInt()
            val month = dateParts[1].toInt() - 1 // Calendar의 월은 0부터 시작
            val day = dateParts[2].toInt()

            calendar.set(year, month, day)
        }

        val datePickerDialog = DatePickerDialog(
            context, R.style.MyDatePickerDialogTheme,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                // 날짜가 선택되면 실행되는 콜백 함수
                val formattedMonth = String.format("%02d", month + 1)
                val formattedDay = String.format("%02d", day)
                val selectedDate = "$year-$formattedMonth-$formattedDay"
                editText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // 다이얼로그 표시
        datePickerDialog.show()
    }
}

