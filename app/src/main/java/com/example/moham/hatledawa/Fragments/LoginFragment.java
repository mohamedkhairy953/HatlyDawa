package com.example.moham.hatledawa.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.hatledawa.Activities.ProfileActivity;
import com.example.moham.hatledawa.Activities.RegisterActivity;
import com.example.moham.hatledawa.Model.UserLab;
import com.example.moham.hatledawa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by moham on 14/02/2017.
 */

public class LoginFragment extends Fragment {
    private EditText mPhoneEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private TextView mSingUp;
    private FirebaseAuth mFirebaseAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    public static LoginFragment newInstance(){
        return new LoginFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_fragment,container,false);
        mSingUp= (TextView) view.findViewById(R.id.sign_up);
        mLoginButton= (Button) view.findViewById(R.id.login);
        mPasswordEditText= (EditText) view.findViewById(R.id.password_edit_text);
        mPhoneEditText= (EditText) view.findViewById(R.id.phone_number);
        mFirebaseAuth=FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    startActivity(ProfileActivity.newIntent(getActivity(),user.getEmail().replace("@yahoo.com",""),mFirebaseAuth));
//                    getActivity().finish();
//                } else {
//                    // User is signed out
//                }
//                // ...
//            }
//        };
        SingUp();
        SingIn();
        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mFirebaseAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mFirebaseAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

    private void SingIn(){
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=mPhoneEditText.getText().toString().trim();
                String password=mPasswordEditText.getText().toString().trim();
               // String regular="^\\+[0-9]{10,13}$";
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                    if(UserLab.getInstance(getActivity(),mFirebaseAuth).login(phone,password));
                 //      getActivity().finish();
                }else{
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SingUp(){
        mSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(RegisterActivity.newIntent(getActivity()));
             getActivity().finish();
            }
        });
    }
}
