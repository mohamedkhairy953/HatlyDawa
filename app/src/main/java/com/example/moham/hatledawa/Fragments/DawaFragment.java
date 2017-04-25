package com.example.moham.hatledawa.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moham.hatledawa.Activities.LoginActivity;
import com.example.moham.hatledawa.Activities.ProfileActivity;
import com.example.moham.hatledawa.Activities.RegisterActivity;
import com.example.moham.hatledawa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by moham on 14/02/2017.
 */

public class DawaFragment extends Fragment {
    public static final String CONNECTION="connection";
    private Button mLoginButton;
    private Button mCreateAccount;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private  FirebaseUser user;

    public static DawaFragment newFragment(String connection){
     Bundle bundle=new Bundle();
     bundle.putSerializable(CONNECTION,connection);
     DawaFragment fragment=new DawaFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view=inflater.inflate(R.layout.activity_dawa,container,false);
        mLoginButton= (Button) view.findViewById(R.id.login_home_page);
        mCreateAccount= (Button) view.findViewById(R.id.create_account_page);
        mFirebaseAuth=FirebaseAuth.getInstance();
      if (getArguments().getSerializable(CONNECTION).equals("true")) {
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                     user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        DatabaseReference mReference= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getEmail().replace("@yahoo.com", ""));
                        mReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Map<String,String> map= (Map<String, String>) dataSnapshot.getValue();
                                startActivity(ProfileActivity.newIntent(getActivity(), user.getEmail().replace("@yahoo.com", ""), mFirebaseAuth,map.get("type")));
                                getActivity().finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    } else {
                        // User is signed out
                    }
                    // ...
                }
            };
      }
        logIn();
        createAccount();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
       if(getArguments().getSerializable(CONNECTION).equals("true")) {
           mFirebaseAuth.addAuthStateListener(mAuthListener);

       }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

   private void logIn(){
     mLoginButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(LoginActivity.newIntent(getActivity()));
             getActivity().finish();
         }
     });
   }

    private void createAccount(){
     mCreateAccount.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
           startActivity(RegisterActivity.newIntent(getActivity()));
             getActivity().finish();
         }
     });
    }


}
