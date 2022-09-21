package com.evontech.evontechpayroll.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.evontech.evontechpayroll.adapter.DayLoginSummaryAdapter
import com.evontech.evontechpayroll.adapter.EventViewPagerAdapter
import com.evontech.evontechpayroll.databinding.FragmentHomeBinding
import com.evontech.evontechpayroll.model.EventAndAnnouncementModel
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel
import com.evontech.evontechpayroll.ui.checkout.ProceedCheckoutViewModel
import com.evontech.evontechpayroll.utils.util
import com.evontech.evontechpayroll.utils.util.Companion.USERFIRSTNAME
import com.evontech.evontechpayroll.utils.util.Companion.USERLASTNAME
import com.evontech.evontechpayroll.utils.util.Companion.USERNAME
import com.evontech.evontechpayroll.utils.util.Companion.getCurrentDate
import com.evontech.evontechpayroll.utils.util.Companion.getMonthAndYear
import com.evontech.evontechpayroll.utils.util.Companion.getStringPreferences
import com.evontech.evontechpayroll.utils.util.Companion.getToDateAfterDays
import com.evontech.evontechpayroll.viewModel.EventAndAnnounceViewModel
import java.text.ParseException
import java.util.*
import kotlin.math.abs


class HomeFragment : Fragment() , EventViewPagerAdapter.RemoveItemInterface {

    private var _binding: FragmentHomeBinding? = null
    lateinit var homeViewModel: HomeViewModel
    lateinit var proceedCheckoutViewModel: ProceedCheckoutViewModel
    private lateinit var eventAndAnnounceViewModel: EventAndAnnounceViewModel
    val adapter = DayLoginSummaryAdapter()
    var recyclerView : RecyclerView?=null

    private val binding get() = _binding!!
    ///// event Section//////
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler : Handler
    private lateinit var eventAdapter: EventViewPagerAdapter


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        homeViewModel =  ViewModelProvider(this).get(HomeViewModel::class.java)
        proceedCheckoutViewModel =  ViewModelProvider(this).get(ProceedCheckoutViewModel::class.java)
        eventAndAnnounceViewModel =  ViewModelProvider(this).get(EventAndAnnounceViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        try {
            if (!getToDateAfterDays(0).equals("Sat") && !getToDateAfterDays(0).equals("Sun")){
                updateDailyAttendance()
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val textView: TextView = binding.textHome
        recyclerView = binding.recyclerViewDays

        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        recyclerView!!.adapter =adapter
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.readDayLogin.observe(viewLifecycleOwner, Observer {
            if (it.size!=0) {
                val hasMap = HashMap<Int, String>()
                for (i in 0..6) {
                    if (i == 0) {
                        hasMap.put(6, getToDateAfterDays(i))
                    } else {
                        hasMap.put(i - 1, getToDateAfterDays(i))
                    }
                }
                for (i in hasMap) {
                    for (j in it) {
                        if (i.value.equals(j.day)) {
                            Collections.swap(it, i.key, it.indexOf(j))
                            break
                        }
                    }
                }
                adapter.setDayListData(it)
            }

        })



        homeViewModel.totleHour.observe(viewLifecycleOwner, Observer {
            binding.tvTotalHours.text=String.format("%.2f", it.toDouble())
        })



        init()
        setTransFormer()
        /*binding.viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,2000)
            }
        })*/

        //eventAndAnnounceViewModel.deleteEvent()

        binding.ivFd.setOnClickListener {
            activity?.let {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://findnerd.com/")
                startActivity(intent)
            }
        }

        getTimeFromAndroid()



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun updateDailyAttendance(){
        homeViewModel.updateDailyLoginAttendance(getToDateAfterDays(0), getCurrentDate().toString())
        homeViewModel.checkInUpdateFirstTime.observe(viewLifecycleOwner,Observer{
            if (it){
                val checkin = getStringPreferences(requireContext(), util.LOGINCHECKIN,"")
                proceedCheckoutViewModel.addCheckIn(MonthlyAttendanceLogModel(
                    null,
                    getStringPreferences(requireContext(), util.USERID,"")!!.toLong(),
                    getToDateAfterDays(0),
                    checkin!!.substring(0, checkin.length - 2),
                    "",
                    getMonthAndYear()
                ))
                homeViewModel.checkInUpdateFirstTimeFlag()
            }
        })

       }

  private fun init() {

      handler = Handler(Looper.myLooper()!!)
      eventAdapter = EventViewPagerAdapter(this,requireContext(),binding.viewPager2)

      binding.viewPager2.offscreenPageLimit =3
      binding.viewPager2.clipToPadding = false
      binding.viewPager2.clipChildren = false
      binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
      binding.viewPager2.adapter = eventAdapter


      eventAndAnnounceViewModel.getAllData.observe(viewLifecycleOwner, Observer() {
        if (it.size>0){
           eventAdapter.addData(it)

        }else{
            eventAndAnnounceViewModel.addEventDetails(EventAndAnnouncementModel(null,"Group Medical Insurance Policy Renewal","14-Oct-2022","https://forms.gle/n5DZv21tLRD6QHWq8","All who are interested to renew/enroll into the StarHealth Group Medical Policy are requested to fill the Form in the link above. Expected Policy Period: Nov 2, 2022 to Nov 1, 2023.","https://picsum.photos/seed/picsum/200/300",""))
            eventAndAnnounceViewModel.addEventDetails(EventAndAnnouncementModel(null,"Employee Details Updation","30-Sep-2022","https://forms.gle/HQZfkUTXx4sC21te9","Kindly help us to stay updated with some key information. Click on the link to fill the form, if any of your personal contact details have changed over the past 1 year.","https://picsum.photos/seed/picsum/200/300","",true))
            eventAndAnnounceViewModel.addEventDetails(EventAndAnnouncementModel(null,"Birthday","15-Sep-2022","https://forms.gle/n5DZv21tLRD6QHWq8","Happy Birth Day","https://picsum.photos/seed/picsum/200/300","",true))
        }

      })

    }

    fun setTransFormer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleX = 0.85f + r * 0.14f
        }
        binding.viewPager2.setPageTransformer(transformer)
    }

    private val runnable =Runnable{
        binding.viewPager2.currentItem =  binding.viewPager2.currentItem +1
    }


    override fun onPause() {
        super.onPause()
       // handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
      //  handler.postDelayed(runnable,2000)
    }

    override fun deleteItem(item: EventAndAnnouncementModel) {
        Log.d("itemClicked=>",item.eventHeading.toString())
        eventAndAnnounceViewModel.removeItem(item)
    }

    private fun getTimeFromAndroid() {
        val dt = Date()
        val hours = dt.hours
        //val min = dt.minutes
        if (hours >= 1 && hours < 12) {
            binding.tvGratingUser.text = "Good Morning, "+ getStringPreferences(requireContext(),
                USERFIRSTNAME,"")+" "+getStringPreferences(requireContext(), USERLASTNAME,"")
            //Toast.makeText(requireContext(), "Good Morning", Toast.LENGTH_SHORT).show()
        } else if (hours >= 12 && hours < 16) {
            binding.tvGratingUser.text ="Good Afternoon, "+ getStringPreferences(requireContext(),
                USERFIRSTNAME,"")+" "+getStringPreferences(requireContext(), USERLASTNAME,"")
            //Toast.makeText(requireContext(), "Good Afternoon", Toast.LENGTH_SHORT).show()
        } else if (hours >= 16 && hours < 21) {
            binding.tvGratingUser.text ="Good Evening, "+ getStringPreferences(requireContext(),
                USERFIRSTNAME,"")+" "+getStringPreferences(requireContext(), USERLASTNAME,"")
            //Toast.makeText(requireContext(), "Good Evening", Toast.LENGTH_SHORT).show()
        } else if (hours >= 21 && hours < 24) {
            binding.tvGratingUser.text ="Good Night, "+ getStringPreferences(requireContext(),
                USERFIRSTNAME,"")+" "+getStringPreferences(requireContext(), USERLASTNAME,"")
            //Toast.makeText(requireContext(), "Good Night", Toast.LENGTH_SHORT).show()
        }
    }


}