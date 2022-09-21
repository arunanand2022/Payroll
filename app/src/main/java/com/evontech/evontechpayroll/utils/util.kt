package com.evontech.evontechpayroll.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.preference.PreferenceManager
import com.evontech.evontechpayroll.R
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class util {
    companion object{
        val USERID = "userId"
        val USERNAME = "userName"
        val USERPHONE = "userMobile"
        val USERFIRSTNAME = "userFirstName"
        val USERLASTNAME = "userLastName"
        val LOGINDAY = "loginDay"
        val LOGINCHECKIN = "loginCheckIn"

        fun getCurrentDate(): String? {//05/09/22 09:05am
            val cal: Calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd/MM/yy HH:mm:ssa", Locale.US)
            return sdf.format(cal.getTime()).toLowerCase()
        }

        fun getCurrentDateTime(): String? {//05/09/22 09:05
            val cal: Calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.US)
            return sdf.format(cal.getTime())
        }

        fun getTodayDateOnly():String{//05/09/22
            val cal: Calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd/MM/yy", Locale.US)
            return sdf.format(cal.getTime())
        }

        fun getMonthAndYear(): String? {//13-SEP-2022
            val cal: Calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
            return sdf.format(cal.getTime())
        }

        fun changeDateFormat(dateVal :String):String{
            val inputSDF = SimpleDateFormat("dd/MM/yy HH:mm:ssa", Locale.US)
            val outputSDF = SimpleDateFormat("dd/MM/yy HH:mma", Locale.US)
            var date: Date? = null
            date = try {
                inputSDF.parse(dateVal)
            } catch (e: ParseException) {
                return dateVal
            }
            return outputSDF.format(date).toLowerCase()
        }



        fun getToDateAfterDays(day: Int?): String {//Mon,Tue,Wed,....
            val nowdate = Date()
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("EEE", Locale.US)
            cal.time = nowdate
            cal.add(Calendar.DATE, day!!)
            return sdf.format(cal.time)
        }

        fun saveStringPreferences(context: Context?, key: String?, value: String?) {
            val faves = PreferenceManager.getDefaultSharedPreferences(context!!)
            val editor = faves.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun getStringPreferences(context: Context?,key: String?,defaultStr: String?): String? {
            val myPrefs = PreferenceManager.getDefaultSharedPreferences(context!!)
            return myPrefs.getString(key, defaultStr)
        }




        fun calculateHours(date1: String, date2: String): String{
            val simpleDateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss")
            //val simpleDateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ssa")
            val startDate: Date = simpleDateFormat.parse(date1)
            val endDate: Date = simpleDateFormat.parse(date2)
            //milliseconds
            var different = endDate.time - startDate.time
            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val elapsedDays = different / daysInMilli
            different = different % daysInMilli
            val elapsedHours = different / hoursInMilli
            different = different % hoursInMilli
            val elapsedMinutes = different / minutesInMilli
            different = different % minutesInMilli
            val elapsedSeconds = different / secondsInMilli
            System.out.printf("%d days, %d hours, %d minutes, %d seconds%n",elapsedDays, elapsedHours, elapsedMinutes , elapsedSeconds)
            val minute:String
            if(elapsedMinutes.toString().length<2){
                minute = ("0"+elapsedMinutes.toString())
            }else{
                minute=  elapsedMinutes.toString()
            }
            val value : String = elapsedHours.toString()+"."+minute
            return value
        }


        fun isLocationEnabled(context: Context): Boolean {
            val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

        fun showGPSNotEnabledDialog(context: Context) {
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.enable_gps))
                .setMessage(context.getString(R.string.required_for_this_app))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.enable_now)) { _, _ ->
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .show()
        }

        fun CalculationByDistance(StartLat: Double,StartLong: Double,EndLat: Double,EndLong: Double): Int {
            var meterInDec: Int
            try {
                Log.d("Location=>",StartLat.toString()+","+StartLong.toString()+","+EndLat.toString()+","+EndLong.toString())
                val Radius = 6371000 // radius of earth in meter
                //int Radius = Integer.parseInt(" 3958.75");// radius of earth in meter
                val dLat = Math.toRadians(EndLat - StartLat)
                val dLon = Math.toRadians(EndLong - StartLong)
                val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)
                        + (Math.cos(Math.toRadians(StartLat))
                        * Math.cos(Math.toRadians(EndLat)) * Math.sin(dLon / 2)
                        * Math.sin(dLon / 2)))
                val c = 2 * Math.asin(Math.sqrt(a))
                val valueResult = Radius * c
                //  double km = valueResult / 1;
                val newFormat = DecimalFormat("####")
                //int kmInDec = Integer.valueOf(newFormat.format(km));
                val meter = valueResult % 1000
                //double meter1 = kmInDec * 1000;
                meterInDec = Integer.valueOf(newFormat.format(meter))
                //Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);
                //Log.e("distanceInMeters",valueResult/10000 "mm");
            } catch (e: Exception) {
                meterInDec = 11
            }
            Log.d("meterInDec=>",meterInDec.toString())
            return meterInDec
        }


    }
}