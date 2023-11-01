package com.example.smallrecord.foodreadypage

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smallrecord.R
import com.example.smallrecord.entity.FoodEntity

class FoodNameSearchedAdapter(
    private val context: Context,
    private val onClickListener: View.OnClickListener
): RecyclerView.Adapter<FoodNameSearchedAdapter.FoodNameSearchedViewHolder>() {

    private val mutableList = mutableListOf<FoodEntity>()

    fun getItem(position: Int): FoodEntity {
        return mutableList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(foodEntityList: List<FoodEntity>) {
        mutableList.clear()
        mutableList.addAll(foodEntityList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = mutableList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int) = R.layout.food_name_searched_view_holder

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FoodNameSearchedViewHolder {
        return FoodNameSearchedViewHolder(LayoutInflater.from(context).inflate(R.layout.food_name_searched_view_holder, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: FoodNameSearchedViewHolder, position: Int) {
        val foodEntity: FoodEntity =mutableList[position]
        viewHolder.foodNameSearchedProductName.text = foodEntity.productName // 검색됨 결과 값

        viewHolder.foodNameSearchedRoot.tag = foodEntity
        viewHolder.foodNameSearchedRoot.setOnClickListener(onClickListener)
    }

    class FoodNameSearchedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val foodNameSearchedRoot: LinearLayout
        val foodNameSearchedProductName: TextView
        init {
            foodNameSearchedRoot = itemView.findViewById(R.id.foodNameSearchedRoot)
            foodNameSearchedProductName = itemView.findViewById(R.id.foodNameSearchedProductName)
        }
    }

}