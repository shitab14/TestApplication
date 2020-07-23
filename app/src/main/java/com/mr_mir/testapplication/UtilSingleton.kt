package com.mr_mir.testapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast

/**
 * Created by Shitab Mir on 21,July,2020
 */
class UtilSingleton {

    companion object {

        fun goToNextActivity(context: Context, targetActivity: Class<out Activity?>?) {
            val i: Intent = Intent(context, targetActivity)
            context.startActivity(i)
        }

        fun showToast(context: Context?, msg: String?) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        fun isNetworkConnected(context: Context): Boolean {
            val conMgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            /** to get info of WIFI N/W :  */
            val wifi = conMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)

            /** to get info of mobile N/W :  */
            val mobile = conMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (wifi.isAvailable && wifi.isConnected) {
                Log.e(
                    "Is Net work? 1", "isNetWork:in 'isNetWork_if' is N/W Connected:"
                            + NetworkInfo.State.CONNECTED
                )
                return true
            } else if (mobile != null && mobile.isAvailable && mobile.isConnected) {
                Log.e(
                    "Is Net work? 2", "isNetWork:in 'isNetWork_if' is N/W Connected:"
                            + NetworkInfo.State.CONNECTED
                )
                return true
            } else if (conMgr.activeNetworkInfo != null && conMgr.activeNetworkInfo.isAvailable
                && conMgr.activeNetworkInfo.isConnected
            ) {
                Log.e(
                    "Is Net work? 3", "isNetWork:in 'isNetWork_if' is N/W Connected:"
                            + NetworkInfo.State.CONNECTED
                )
                return true
            }
            return false
        }


    }

}