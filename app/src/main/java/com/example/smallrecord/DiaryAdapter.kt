package com.example.smallrecord

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class DiaryAdapter(private val context: Activity, private val arrayList: ArrayList<DiaryItem>) : ArrayAdapter<DiaryItem>(context, R.layout.diaryitem, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.diaryitem, null)

        val time: TextView = view.findViewById(R.id.diaryItemTime)
        val image: ImageView = view.findViewById(R.id.diaryItemImage)
        val doName: TextView = view.findViewById(R.id.diaryItemName)
        val button: ImageView = view.findViewById(R.id.diaryItemButton)

        time.text = arrayList[position].time
        image.setImageResource(arrayList[position].image)
        doName.text = arrayList[position].doName
        button.setImageResource(arrayList[position].button)

        view.setOnClickListener {
            showSecondDialog(arrayList[position], position)
        }

        return view
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < arrayList.size) {
            arrayList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    private fun showSecondDialog(diaryItem: DiaryItem, position: Int) {
        val dialog = Dialog(context, R.style.TransparentDialogStyle)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_readdiary, null)
        dialog.setContentView(dialogView)

        val starttime = dialogView.findViewById<TextView>(R.id.starttime)
        val finishtime = dialogView.findViewById<TextView>(R.id.finishtime)

        starttime.text = "시작 시간: ${diaryItem.time}"
        finishtime.text = "완료 시간: ${diaryItem.time}"

        val plus = dialogView.findViewById<ImageButton>(R.id.save)
        plus.setOnClickListener {
            dialog.dismiss()
        }

        val remove = dialogView.findViewById<ImageButton>(R.id.remove)
        remove.setOnClickListener {
            removeItem(position)
            dialog.dismiss()
        }

        dialog.show()
    }
}
