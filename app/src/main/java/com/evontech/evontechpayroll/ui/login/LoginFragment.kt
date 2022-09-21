package com.evontech.evontechpayroll.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.evontech.evontechpayroll.activity.MainActivity
import com.evontech.evontechpayroll.databinding.FragmentLoginBinding
import com.evontech.evontechpayroll.model.UserDetailsModel
import com.evontech.evontechpayroll.utils.util
import com.evontech.evontechpayroll.utils.util.Companion.CalculationByDistance
import com.evontech.evontechpayroll.utils.util.Companion.getCurrentDate
import com.evontech.evontechpayroll.utils.util.Companion.getStringPreferences
import com.evontech.evontechpayroll.utils.util.Companion.saveStringPreferences
import com.google.android.gms.location.*
import kotlin.Boolean
import kotlin.Exception
import kotlin.let


class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding
    lateinit var loginViewModel : LoginViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var latitude: Double?= 0.0
    var longtitude: Double?= 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        var root : View = binding!!.root

        getCurrentLocation()

        setActions()

        return root
    }

    fun setActions(){
        try {

            loginViewModel.checkUserInsertedOrNot(UserDetailsModel(null,"arun.anand","1234","Arun","","Anand",
                "arun.anand@evontech.com","8851065052",getCurrentDate()))
            loginViewModel.checkUserAdded.observe(viewLifecycleOwner, Observer {
                if (it==true){
                    loginViewModel.addUserDetails(UserDetailsModel(null,"arun.anand","1234","Arun","","Anand",
                        "arun.anand@evontech.com","8851065052",getCurrentDate()))
                }
            })
        }catch (e:Exception){
            Toast.makeText(requireContext(),"InsertUser failed!!",Toast.LENGTH_LONG).show()
        }



        binding!!.btnSignIn.setOnClickListener {
            if(checkValidation()){
                val userData = UserDetailsModel(null,binding!!.etUsername.text.toString(),binding!!.etPassword.text.toString())
                loginViewModel.login(userData)
            }


        }

        loginViewModel.errorUsername .observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                //Toast.makeText(requireContext(), "User doesnt exist,please Register!", Toast.LENGTH_SHORT).show()
                binding!!.etUsername.error = "Please check username"
                binding!!.etUsername.requestFocus()
                loginViewModel.donetoastErrorUsername()
            }
        })
        loginViewModel.errorInvalidPassword.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                //Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                binding!!.etPassword.error = "Please check your password"
                binding!!.etPassword.requestFocus()
                loginViewModel.donetoastInvalidPassword()
            }
        })
        loginViewModel.navigateToHome.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                if (binding!!.cbRemember.isChecked){
                    saveStringPreferences(requireContext(),"userName",binding!!.etUsername.text.toString())
                    saveStringPreferences(requireContext(),"userPass",binding!!.etPassword.text.toString())
                }
                //if(CalculationByDistance(latitude!!,longtitude!!,30.3649305, 78.0880018)<=40){
                    navigateToDashboard()
                    loginViewModel.doneNavigatingUserDetails()
                    stopLocationUpdates()
                /*}else{
                   Toast.makeText(requireActivity(), "Your are out side of office location!!",
                       Toast.LENGTH_SHORT).show()
                }*/
            }
        })

        if (!getStringPreferences(requireContext(),"userName","").equals("")){
            val username = getStringPreferences(requireContext(),"userName","")
            val userpass = getStringPreferences(requireContext(),"userPass","")
            binding!!.etUsername.text = Editable.Factory.getInstance().newEditable(username)
            binding!!.etPassword.text = Editable.Factory.getInstance().newEditable(userpass)
            binding!!.cbRemember.isChecked = true

        }


    }

    private fun navigateToDashboard() {
        Toast.makeText(requireContext(), "Login Successfully!!", Toast.LENGTH_SHORT).show()
        saveStringPreferences(requireContext(),"logoutDateTime","")
         activity?.let{
             val intent =  Intent(it,MainActivity::class.java)
             startActivity(intent)
         }
        requireActivity().finish()
    }
    fun checkValidation():Boolean{
        var status : Boolean
        if(isEmpty(binding!!.etUsername.text.toString().trim())){
            binding!!.etUsername.error = "Please enter your username"
            binding!!.etUsername.requestFocus()
            status = false
        }else if(isEmpty(binding!!.etPassword.text.toString().trim())){
            binding!!.etPassword.error = "Please enter your password"
            binding!!.etPassword.requestFocus()
            status = false
        }else{
            status = true
        }
        return status
    }

    override fun onStart() {
        super.onStart()
        when {
            util.isLocationEnabled(requireActivity()) -> {
                //setUpLocationListener()
            }
            else -> {
                util.showGPSNotEnabledDialog(requireActivity())
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        var mLocationRequest = LocationRequest()
        mLocationRequest.interval = 10000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = Priority.PRIORITY_HIGH_ACCURACY
        mLocationRequest.smallestDisplacement = 8F

        fusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallBack, Looper.myLooper())
    }
    private val mLocationCallBack = object :LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            var location = p0.lastLocation
            updateUiWithLocation(location!!)

        }
    }
    fun updateUiWithLocation(location: Location){
        latitude = location.latitude
        longtitude = location.longitude
        Toast.makeText(requireActivity(), location.latitude.toString()+","+location.longitude.toString(),
            Toast.LENGTH_SHORT).show()
        binding!!.tvLocation.text = location.latitude.toString()+","+location.longitude.toString()
    }
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(mLocationCallBack)
    }

}






