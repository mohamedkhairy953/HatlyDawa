package com.example.moham.hatledawa.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.moham.hatledawa.Fragments.RegisterFragment;
import com.example.moham.hatledawa.SingleFragmentActivity;

/**
 * Created by moham on 14/02/2017.
 */

public class RegisterActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,RegisterActivity.class);
        return intent;
    }
    @Override
    public Fragment CreateFragment() {
        return RegisterFragment.newInstance();
    }
}
