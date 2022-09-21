package com.evontech.evontechpayroll.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.internal.ConnectionCallbacks
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.google.android.gms.location.*


class GetMyCurrentLocation(context: Context)  {
    // LogCat tag
    private val TAG: String = GetMyCurrentLocation::class.java.getSimpleName()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var locationRequest: LocationRequest? = null
    // Location updates intervals in sec
    private val UPDATE_INTERVAL = 1000 // 10 sec
    private val FATEST_INTERVAL = 500 // 5 sec
    private val DISPLACEMENT = 10 // 10 meters

    var mContext: Context? = null

    // flag for GPS status
    var canGetLocation = true
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false
    var location : Location? = null
    var latitude = 0.0
    var longitude = 0.0





    @SuppressLint("RestrictedApi")
    protected fun createLocationRequest() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext!!)
        locationRequest = LocationRequest()
        locationRequest!!.setInterval(UPDATE_INTERVAL.toLong())
        locationRequest!!.setFastestInterval(FATEST_INTERVAL.toLong())
        locationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest!!.setSmallestDisplacement(DISPLACEMENT.toFloat())
    }

   @SuppressLint("MissingPermission")
   fun getCurrentLocation(){
       fusedLocationClient.requestLocationUpdates(locationRequest!!,mLocationCallBack, Looper.getMainLooper())
   }
    private val mLocationCallBack = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            var location = p0.lastLocation
            //updateUiWithLocation(location!!)

        }
    }






}



