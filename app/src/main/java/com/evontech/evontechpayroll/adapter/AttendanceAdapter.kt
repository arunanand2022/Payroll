package com.evontech.evontechpayroll.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evontech.evontechpayroll.databinding.AttendanceItemBinding
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel

class AttendanceAdapter():RecyclerView.Adapter<AttendanceAdapter.ViewHolder>() {
    lateinit var attendanceBinding: AttendanceItemBinding
    var attendanceList = emptyList<MonthlyAttendanceLogModel>()
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): AttendanceAdapter.ViewHolder {
        attendanceBinding = AttendanceItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(attendanceBinding)
    }

    override fun onBindViewHolder(holder: AttendanceAdapter.ViewHolder, position: Int) {
        var datalist = attendanceList[position]
        holder.bind(datalist)

    }

    override fun getItemCount(): Int {
        return attendanceList.size
    }

    class ViewHolder(private val binding: AttendanceItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(dataList:MonthlyAttendanceLogModel){
            binding.modelData =dataList
        }

    }

    fun bindAttendanceData(model: List<MonthlyAttendanceLogModel>){
        attendanceList = model
        notifyDataSetChanged()
    }


}