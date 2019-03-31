package com.fast.guide.Connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public  class CheckNetwork {

    public  boolean isInternetAvailable(Context context)
    {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                return true;
            }
            else
            {
                return true;
            }

        }
    }
}