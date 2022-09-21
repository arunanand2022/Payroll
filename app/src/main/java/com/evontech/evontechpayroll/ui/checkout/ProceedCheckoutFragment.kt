package com.evontech.evontechpayroll.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.evontech.evontechpayroll.R
import com.evontech.evontechpayroll.activity.LoginPayrollActivity
import com.evontech.evontechpayroll.adapter.AttendanceAdapter
import com.evontech.evontechpayroll.adapter.ProceedCheckoutAdapter
import com.evontech.evontechpayroll.databinding.FragmentProceedCheckoutBinding
import com.evontech.evontechpayroll.model.MonthlyAttendanceLogModel
import com.evontech.evontechpayroll.model.UserDetailsModel
import com.evontech.evontechpayroll.ui.home.HomeViewModel
import com.evontech.evontechpayroll.ui.login.LoginViewModel
import com.evontech.evontechpayroll.utils.util
import com.evontech.evontechpayroll.utils.util.Companion.LOGINCHECKIN
import com.evontech.evontechpayroll.utils.util.Companion.USERID
import com.evontech.evontechpayroll.utils.util.Companion.getCurrentDate
import com.evontech.evontechpayroll.utils.util.Companion.getCurrentDateTime
import com.evontech.evontechpayroll.utils.util.Companion.getMonthAndYear
import com.evontech.evontechpayroll.utils.util.Companion.getStringPreferences
import com.evontech.evontechpayroll.utils.util.Companion.getToDateAfterDays

class ProceedCheckoutFragment : Fragment() {

    private var _binding: FragmentProceedCheckoutBinding? = null

    private lateinit var proceedCheckoutViewModel: ProceedCheckoutViewModel
    private lateinit var userDetailsViewModel : LoginViewModel
    private lateinit var homeViewModel: HomeViewModel
    private val binding get() = _binding!!

    private var adapter = ProceedCheckoutAdapter()

    var hourValue :String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        proceedCheckoutViewModel =ViewModelProvider(this).get(ProceedCheckoutViewModel::class.java)
        userDetailsViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentProceedCheckoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textGallery
        proceedCheckoutViewModel.text.observe(viewLifecycleOwner) {
        //    textView.text = it
        }
        //Log.d("month=>",getMonthAndYear().toString())
        binding.btnSubmit.setOnClickListener {
            submitCheckOutData()
        }
        binding.btnCheckout.setOnClickListener {
            updateCheckOutFinalTime()
        }


        setCheckoutListData()
        return root
    }
    fun submitCheckOutData(){
        if (checkValidation()){
            val uid = getStringPreferences(requireContext(),USERID,"")!!.toLong()
            val checkin = getStringPreferences(requireContext(),LOGINCHECKIN,"")
            val checkout = getCurrentDate()
            val month_year = getMonthAndYear()
            val hour = binding.etHoursWorked.text.toString()
            val projectname = binding.spinProjectName.selectedItem.toString()
            val stage = binding.spinStage.selectedItem.toString()
            val comment = binding.etComment.text.toString()
            proceedCheckoutViewModel.updateCheckoutSave(MonthlyAttendanceLogModel(null,uid, getToDateAfterDays(0),checkin!!.substring(0, checkin!!.length - 2),checkout!!.substring(0, checkout!!.length - 2),month_year,
            projectname,stage,comment,"",hour))
        }
    }
    fun checkValidation():Boolean{
        if (isEmpty(binding.etHoursWorked.text.toString())){
            binding.etHoursWorked.error = "Please enter hours"
            binding.etHoursWorked.requestFocus()
            return false
        }else if (binding.spinProjectName.selectedItem.equals("Select Project")){
            Toast.makeText(requireContext(),"Please select project name",Toast.LENGTH_LONG).show()
            binding.spinProjectName.requestFocus()
            return false
        }else if (binding.spinStage.selectedItem.equals("Select Stage")){
            Toast.makeText(requireContext(),"Please select stage",Toast.LENGTH_LONG).show()
            binding.spinStage.requestFocus()
            return false
        }else{
            return true
        }
    }

    fun setCheckoutListData(){
        binding.recyclerViewCheckout.adapter = adapter
        binding.recyclerViewCheckout.layoutManager = LinearLayoutManager(requireActivity())

        proceedCheckoutViewModel.getTodayCheckOutData.observe(viewLifecycleOwner, Observer {
           if(it.size>0){
               if(it.get(0).projectName != null){
                   binding.llPart1.visibility=View.VISIBLE
                   adapter.setCheckOutListData(it)
                   hourValue = it.get(0).hour
               }else{
                   binding.llPart1.visibility=View.GONE
               }
           }
        })
    }

    fun updateCheckOutFinalTime(){
        val checkout = getCurrentDate()
        val month_year = getMonthAndYear()
        val hour = hourValue //binding.etHoursWorked.text.toString()
        Log.d("hourValue=>",hourValue.toString())
        proceedCheckoutViewModel.updateCheckOutFinalTime(MonthlyAttendanceLogModel(null,null, getToDateAfterDays(0),"",checkout!!.substring(0, checkout!!.length - 2),month_year,
            "","","","",hour))

        proceedCheckoutViewModel.updateFinalCheckoutFlag.observe(viewLifecycleOwner, Observer {
            if (it){
                homeViewModel.updateCheckoutTime(getToDateAfterDays(0),hour!!,checkout)
                Toast.makeText(requireContext(),"Checkout Successfully!!",Toast.LENGTH_LONG).show()
                activity?.apply {
                    util.saveStringPreferences(getApplication(),"logoutDateTime",getCurrentDate())//08/09/22 12:28:57pm
                    val intent =  Intent(this, LoginPayrollActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }else{
                Toast.makeText(requireContext(),"Some technical issue!!",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        val item = menu.findItem(R.id.action_refresh)
        item.isVisible = false
    }


}