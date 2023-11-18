package com.example.smallrecord

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import java.util.*

class DatePickerHandler(private val context: Context, private val editText: EditText) {

    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context, R.style.MyDatePickerDialogTheme,
            DatePickerDialog.OnDateSetListener { view: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                // 날짜가 선택되면 실행되는 콜백 함수
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                editText.setText(selectedDate)
            },
            year,
            month,
            day
        )

        // 캘린더의 현재 날짜를 기본값으로 설정
        datePickerDialog.datePicker.init(year, month, day, null)

        // 다이얼로그 표시
        datePickerDialog.show()
    }
}

