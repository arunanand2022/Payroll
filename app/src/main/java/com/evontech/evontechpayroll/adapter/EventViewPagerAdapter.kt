package com.evontech.evontechpayroll.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.evontech.evontechpayroll.R
import com.evontech.evontechpayroll.model.EventAndAnnouncementModel


class EventViewPagerAdapter(val removeItemInterface: RemoveItemInterface,val context: Context,val viewPager:ViewPager2):RecyclerView.Adapter<EventViewPagerAdapter.ViewHolder>() {

    var itemListData = emptyList<EventAndAnnouncementModel>()

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): EventViewPagerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewPagerAdapter.ViewHolder, position: Int) {
        val itemList = itemListData[position]

        if(itemList.eventHeading.equals("Birthday")){
            holder.headerName.text = itemList.eventHeading+"- Arun Anand"
            holder.requested.visibility = View.GONE
            holder.url.visibility = View.GONE
            holder.detail.visibility = View.GONE
            holder.ll_bd_part.visibility = View.VISIBLE
            Glide.with(holder.image.getContext()).load(itemList.image).into(holder.image)
        }else{
            holder.requested.visibility = View.VISIBLE
            holder.url.visibility = View.VISIBLE
            holder.detail.visibility = View.VISIBLE
            holder.ll_bd_part.visibility = View.GONE
            holder.headerName.text = itemList.eventHeading
            holder.requested.text = "Req By: "+itemList.requested_date
            holder.url.text = "Url: "+itemList.url
            holder.detail.text = itemList.details
        }

        holder.url.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(itemList.url)
            context.startActivity(intent)
        }

        holder.headerName.setOnClickListener {
            removeItemInterface.deleteItem(itemList)
        }



        if (position== itemListData.size-1){
            viewPager.post { runnable }
        }
    }

    override fun getItemCount(): Int {
        return itemListData.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById(R.id.user_image) as ImageView
        val headerName = itemView.findViewById(R.id.heading_title) as TextView
        val requested = itemView.findViewById(R.id.tv_requested) as TextView
        val url = itemView.findViewById(R.id.tv_url) as TextView
        val detail = itemView.findViewById(R.id.tv_details) as TextView
        val ll_bd_part = itemView.findViewById(R.id.ll_bd_part) as LinearLayout

    }

    private val runnable = Runnable{
        itemListData = itemListData
        notifyDataSetChanged()
    }

    fun addData(data: List<EventAndAnnouncementModel>){
        itemListData = data
        notifyDataSetChanged()
    }

    interface RemoveItemInterface{
        fun deleteItem(item : EventAndAnnouncementModel)
    }
}