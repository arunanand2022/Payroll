package com.evontech.evontechpayroll.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evontech.evontechpayroll.databinding.ProceedCheckoutItemBinding
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel

class ProceedCheckoutAdapter: RecyclerView.Adapter<ProceedCheckoutAdapter.ViewHolder>() {

    private lateinit var binding : ProceedCheckoutItemBinding

    private var checkOutList = emptyList<MonthlyAttendanceLogModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ProceedCheckoutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  itemData = checkOutList[position]
        holder.bind(itemData)
    }

    override fun getItemCount(): Int {
        return checkOutList.size
    }


    class ViewHolder(private val binding: ProceedCheckoutItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: MonthlyAttendanceLogModel){
            binding.modelData = data
        }
    }


    fun setCheckOutListData(checkoutList : List<MonthlyAttendanceLogModel>){
        this.checkOutList = checkoutList
        notifyDataSetChanged()


    }
}