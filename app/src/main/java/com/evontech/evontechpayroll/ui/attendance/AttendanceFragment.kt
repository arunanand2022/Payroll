package com.evontech.evontechpayroll.ui.attendance

import android.app.Application
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.evontech.evontechpayroll.R
import com.evontech.evontechpayroll.adapter.AttendanceAdapter
import com.evontech.evontechpayroll.databinding.FragmentAttendanceBinding
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel
import com.evontech.evontechpayroll.utils.util.Companion.getMonthAndYear


class AttendanceFragment : Fragment() {
    lateinit var binding: FragmentAttendanceBinding
    var adapter = AttendanceAdapter()
    lateinit var attendanceViewModel: AttendanceViewModel
    var adaptermonth: ArrayAdapter<String>?=null
    var adapterYear: ArrayAdapter<String>?=null

    var monthArr =  arrayOf<String>("Select Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug","Sep","Oct","Nov","Dec")
    var yearArr = arrayOf<String>("Select Year","2022","2021","2020")

    var factory : AttendanceViewModelFactory?=null
    val monthVal = getMonthAndYear()!!.split("-")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        factory =  AttendanceViewModelFactory(Application(), monthVal[1]+"-"+monthVal[2])
        attendanceViewModel = ViewModelProvider(this,factory!!).get(AttendanceViewModel::class.java)
        binding = FragmentAttendanceBinding.inflate(inflater,container,false)
        val root: View =binding.root


        setAction()
        setListOfAttendance()




        return root
    }

    fun setAction(){
        activity?.apply {
            adaptermonth = ArrayAdapter(this, android.R.layout.simple_spinner_item, monthArr)
            adaptermonth!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinMonth.adapter = adaptermonth

            adapterYear = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearArr)
            adapterYear!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinYear.adapter = adapterYear
        }

        binding.spinMonth.setSelection(adaptermonth!!.getPosition(monthVal[1]))
        binding.spinYear.setSelection(adapterYear!!.getPosition(monthVal[2]))


        binding.btnSearch.setOnClickListener {
            val mon = binding.spinMonth.selectedItem.toString()
            val yer = binding.spinYear.selectedItem.toString()
            //factory =  AttendanceViewModelFactory(Application(), mon+"-"+yer)
           // attendanceViewModel = ViewModelProvider(this,factory!!).get(AttendanceViewModel::class.java)

            attendanceViewModel.getMonthData(mon+"-"+yer)
            setListOfAttendance()
        }


        binding.recyclerViewAttendance.adapter = adapter
        binding.recyclerViewAttendance.layoutManager =LinearLayoutManager(requireActivity())

    }
    fun setListOfAttendance(){
        attendanceViewModel.getTodayCheckOutData.observe(viewLifecycleOwner, Observer {
            adapter.bindAttendanceData(it)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        val item = menu.findItem(R.id.action_refresh)
        item.isVisible = false
    }



}