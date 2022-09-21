package com.evontech.evontechpayroll.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.evontech.evontechpayroll.R
import com.evontech.evontechpayroll.model.DayLoginSummaryModel
import com.evontech.evontechpayroll.utils.util.Companion.changeDateFormat

class DayLoginSummaryAdapter : RecyclerView.Adapter<DayLoginSummaryAdapter.ViewHolder>() {
    private var dayLoginList = emptyList<DayLoginSummaryModel>()

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): DayLoginSummaryAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.day_login_summary_item,parent,false))
    }

    override fun onBindViewHolder(holder: DayLoginSummaryAdapter.ViewHolder, position: Int) {
        val listData = dayLoginList[position]

        if (listData.day.equals("Sat") || listData.day.equals("Sun")){
            holder.ll_part.setBackgroundColor(Color.parseColor("#DCDBDB"))
            holder.textView_day.text = listData.day
            holder.textView_checkin.text = ""
            holder.textView_checkout.text = ""
            holder.textView_hours.text = ""
        }else{
            holder.ll_part.setBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.textView_day.text = listData.day
            if (listData.checkIn.equals("0")){
                holder.textView_checkin.text = ""
                holder.textView_hours.text = ""
            }else{
                holder.textView_checkin.text = changeDateFormat(listData.checkIn.toString())
                holder.textView_hours.text = listData.hours
                //holder.textView_hours.setTextColor(Color.parseColor("#CD0606"))
            }
            if (listData.checkOut.equals("0"))
                holder.textView_checkout.text = ""
            else
                holder.textView_checkout.text = changeDateFormat(listData.checkOut.toString())


            if(!listData.hours.equals(""))
                if (listData.hours!!.toDouble()< 9.00 )
                    holder.textView_hours.setTextColor(Color.parseColor("#CD0606"))
                else
                    holder.textView_hours.setTextColor(Color.parseColor("#000000"))
        }

    }

    override fun getItemCount(): Int {
        return dayLoginList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView_day: TextView = itemView.findViewById(R.id.tv_day)
        val textView_checkin: TextView = itemView.findViewById(R.id.tv_check_in)
        val textView_checkout: TextView = itemView.findViewById(R.id.tv_check_out)
        val textView_hours: TextView = itemView.findViewById(R.id.tv_hours)
        val ll_part: LinearLayout = itemView.findViewById(R.id.ll_part)
    }


    fun setDayListData(dayList : List<DayLoginSummaryModel>){
        this.dayLoginList = dayList
        notifyDataSetChanged()


    }
}