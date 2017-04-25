package com.example.moham.hatledawa.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moham.hatledawa.Activities.ProfileActivity;
import com.example.moham.hatledawa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by moham on 26/02/2017.
 */

public class UpgradeFragment extends DialogFragment {
    public static final String PHONE_REQUEST="PHONE_REQUEST";
    public static final String REQUEST="REQUEST";

    private DatabaseReference mDatabaseReference;
    private String phone;
    private EditText pharmacyNumberEditText;
    private EditText pharmacyLicenseEditText;
    private Button upgradeButton;
    private Button cancelButton;
    private AlertDialog dialog;
    private ProgressDialog mProgressDialog;
    private static FirebaseAuth mFirebaseAuth;

    public static UpgradeFragment newFragment(String phone , FirebaseAuth firebaseAuth){
        Bundle  bundle=new Bundle();
        bundle.putSerializable(PHONE_REQUEST,phone);
        UpgradeFragment fragment=new UpgradeFragment();
        mFirebaseAuth=firebaseAuth;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        phone= (String) getArguments().getSerializable(PHONE_REQUEST);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.upgride_view,null);
        pharmacyLicenseEditText= (EditText) view.findViewById(R.id.pharmacy_lisence_edit_text);
        pharmacyNumberEditText= (EditText) view.findViewById(R.id.pharmacy_phone_edit_text);
        upgradeButton= (Button) view.findViewById(R.id.upgride);
        cancelButton= (Button) view.findViewById(R.id.cancel_upgrade);
        mProgressDialog=new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Upgrade ......");
        dialog=new AlertDialog.Builder(getActivity())
                .setView(view).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        updrade();
        Toast.makeText(getActivity(), pharmacyNumberEditText.getText().toString()+":"+pharmacyLicenseEditText.getText().toString(), Toast.LENGTH_SHORT).show();

        cancel();
        return dialog;
    }


    private void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void updrade(){
        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.show();
                if (!TextUtils.isEmpty( pharmacyNumberEditText.getText().toString().trim()) && !TextUtils.isEmpty(pharmacyNumberEditText.getText().toString().trim()) && android.util.Patterns.PHONE.matcher(pharmacyNumberEditText.getText().toString().trim()).matches()) {
                    mDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                           // mDatabaseReference.child("phone").setValue(pharmacyNumberEditText.getText().toString().trim());
                            mDatabaseReference.child("username").setValue(map.get("username"));
                            mDatabaseReference.child("pharmacylicense").setValue(pharmacyLicenseEditText.getText().toString().trim());
                            mDatabaseReference.child("type").setValue("doctor");
                            mProgressDialog.dismiss();
                            startActivity(ProfileActivity.newIntent(getActivity(),phone,mFirebaseAuth,"doctor"));
                            getActivity().finish();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    dialog.dismiss();
                }else{
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), "Upgrade is Fail ", Toast.LENGTH_SHORT).show();

                }
            }
        });
     }

}
