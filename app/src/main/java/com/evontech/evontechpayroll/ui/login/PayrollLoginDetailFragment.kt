package com.evontech.evontechpayroll.ui.login

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.evontech.evontechpayroll.R
import com.evontech.evontechpayroll.databinding.FragmentPayrollLoginDetailBinding
import com.evontech.evontechpayroll.model.DayLoginSummaryModel
import com.evontech.evontechpayroll.ui.home.HomeViewModel
import com.evontech.evontechpayroll.utils.ManagePermissions
import com.evontech.evontechpayroll.utils.util.Companion.isLocationEnabled
import com.evontech.evontechpayroll.utils.util.Companion.showGPSNotEnabledDialog


class PayrollLoginDetailFragment : Fragment() {
    private var _binding : FragmentPayrollLoginDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var homeViewModel: HomeViewModel

    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
         homeViewModel =  ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentPayrollLoginDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /// Permissions///////
        permissionAllow()


        binding.btnSwitchToLogin.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                if (managePermissions.checkPermissions()){
                    findNavController().navigate(R.id.action_payrollLoginDetailFragment_to_loginFragment)

                }
        }




        insertLoginDay()

        return root
    }

    fun insertLoginDay(){
        homeViewModel.readDayLogin.observe(viewLifecycleOwner, Observer {
            if (it.size==0){
                homeViewModel.addDayLogin(DayLoginSummaryModel(null,"Mon","0","0","0.00"))
                homeViewModel.addDayLogin(DayLoginSummaryModel(null,"Tue","0","0","0.00"))
                homeViewModel.addDayLogin(DayLoginSummaryModel(null,"Wed","0","0","0.00"))
                homeViewModel.addDayLogin(DayLoginSummaryModel(null,"Thu","0","0","0.00"))
                homeViewModel.addDayLogin(DayLoginSummaryModel(null,"Fri","0","0","0.00"))
                homeViewModel.addDayLogin(DayLoginSummaryModel(null,"Sat","0","0","0.00"))
                homeViewModel.addDayLogin(DayLoginSummaryModel(null,"Sun","0","0","0.00"))
            }
        })

    }

    fun permissionAllow(){
        val list = listOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
        )
        managePermissions = ManagePermissions(requireActivity(),list,PermissionsRequestCode)
    }




}