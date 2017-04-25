package com.example.moham.hatledawa.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.moham.hatledawa.Fragments.DawaFragment;
import com.example.moham.hatledawa.R;
import com.example.moham.hatledawa.SingleFragmentActivity;


public class DawaActivity extends SingleFragmentActivity {

    @Override
    public Fragment CreateFragment() {
        if (isNetworkAvailableAndConnected()&& isNetworkConnected()) {
            return DawaFragment.newFragment("true");
        }
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        return  DawaFragment.newFragment("false");
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public boolean isNetworkAvailableAndConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isAvailable=cm.getActiveNetworkInfo()!=null;
        boolean isConnected=isAvailable && cm.getActiveNetworkInfo().isConnected();
        return isConnected;
    }
}
