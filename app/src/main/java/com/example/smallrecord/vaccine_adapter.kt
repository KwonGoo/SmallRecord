package com.example.smallrecord

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smallrecord.databinding.VaccineSectionBinding

class vaccine_adapter(val List: List<vaccine_section>) : RecyclerView.Adapter<vaccine_adapter.MyViewHolder>() {

    inner class MyViewHolder( val viewDataBinding: VaccineSectionBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(vaccine_section: vaccine_section) {
            viewDataBinding.txtCategory.text = vaccine_section.category
            viewDataBinding.rvSubVaccine.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(viewDataBinding.root.context)
                adapter = vaccine_subadapter(vaccine_section.vaccinelist)
                addItemDecoration(DividerItemDecoration(viewDataBinding.root.context, DividerItemDecoration.VERTICAL))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(VaccineSectionBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(List[position])
    }

    override fun getItemCount(): Int {
        return List.size
    }
}
