package com.example.smallrecord

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class DiaryAdapter(private val context: Activity, private val arrayList: ArrayList<DiaryItem>) : ArrayAdapter<DiaryItem> (context,
    R.layout.diaryitem,arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.diaryitem,null)

        val time : TextView = view.findViewById(R.id.diaryItemTime)
        val image : ImageView = view.findViewById(R.id.diaryItemImage)
        val doName : TextView = view.findViewById(R.id.diaryItemName)
        val button : ImageView = view.findViewById(R.id.diaryItemButton)

        time.text = arrayList[position].time
        image.setImageResource(arrayList[position].image)
        doName.text = arrayList[position].doName
        button.setImageResource(arrayList[position].button)


        return view
    }
}