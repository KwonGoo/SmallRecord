package com.example.smallrecord

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smallrecord.databinding.VaccineItemBinding

class vaccine_subadapter(
    private val vaccineList: List<vaccine_item>
) : RecyclerView.Adapter<vaccine_subadapter.MyViewHolder>() {

    class MyViewHolder(
        private val viewDataBinding: VaccineItemBinding
    ) : RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(vaccine_item: vaccine_item) {
            with(viewDataBinding) {
                vaccineName.text = vaccine_item.vaccineName
                vaccineCause.text = vaccine_item.diseaseName
                vaccineCount.text = vaccine_item.vaccinationCount
                vaccineDay.text = vaccine_item.vaccinationDate
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(VaccineItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(vaccineList[position])
    }

    override fun getItemCount() = vaccineList.size
}