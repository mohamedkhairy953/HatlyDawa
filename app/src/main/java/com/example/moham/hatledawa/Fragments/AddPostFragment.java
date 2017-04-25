package com.example.moham.hatledawa.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moham.hatledawa.Model.PostLab;
import com.example.moham.hatledawa.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.UUID;

/**
 * Created by moham on 19/02/2017.
 */

public class AddPostFragment extends DialogFragment {
    public static final String USER_URI="user_uri";
    public static final String USER_NAME="user_name";
    public static final String PHONE="animation";
    private EditText drugName;
    private EditText drugType;
    private EditText drugCon;
    private ImageView userImageView;
    private TextView userName;
    private Button postButton;
    private Button cancelButton;
    private AlertDialog dialog;
    private DatabaseReference mDatabaseReference;


    public static AddPostFragment newFragment(String userUri, String username,String phone){
        Bundle  bundle=new Bundle();
        bundle.putSerializable(USER_NAME,username);
        bundle.putSerializable(USER_URI,userUri);
       bundle.putSerializable(PHONE,phone);
        AddPostFragment fragment=new AddPostFragment();
        fragment.setArguments(bundle);
        return  fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.add_post,null);
        postButton= (Button) view.findViewById(R.id.post_add_post);
        cancelButton= (Button) view.findViewById(R.id.cancel_add_post);
        drugName= (EditText) view.findViewById(R.id.drug_name_add_post);
        drugType= (EditText) view.findViewById(R.id.drug_type_add_post);
        drugCon= (EditText) view.findViewById(R.id.drug_con_add_post);
        userImageView= (ImageView) view.findViewById(R.id.poster_image_view);
        userName= (TextView) view.findViewById(R.id.poster_name);
        dialog=new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
      //  dialog.getWindow().getAttributes().windowAnimations = (int) getArguments().getSerializable(ANIMATION);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Posts");
        Picasso.with(getActivity()).load(Uri.parse((String) getArguments().getSerializable(USER_URI))).into(userImageView);
        userName.setText((String) getArguments().getSerializable(USER_NAME));
        post();
        cancel();
        return super.onCreateDialog(savedInstanceState);
    }

    private void post(){
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String drugname=drugName.getText().toString();
                String drugtype=drugType.getText().toString();
                String drugcon=drugCon.getText().toString();
                PostLab.getInstance().addPost((String) getArguments().getSerializable(PHONE),(String) getArguments().getSerializable(USER_NAME)
                ,drugname,drugtype,drugcon,String.valueOf(0),(String) getArguments().getSerializable(USER_URI));
                DatabaseReference mReference=mDatabaseReference.child("all");
                String uuid=String.valueOf(UUID.randomUUID());

                DatabaseReference reference=mReference.child(uuid);
                reference.child("uuid").setValue(uuid);
                reference.child("image").setValue( getArguments().getSerializable(USER_URI));
                reference.child("posterName").setValue( getArguments().getSerializable(USER_NAME));
                reference.child("drugName").setValue(drugname);
                reference.child("drugType").setValue(drugtype);
                reference.child("drugCon").setValue(drugcon);
                reference.child("doctorNumber").setValue("0");
                dialog.dismiss();
            }
        });
    }

    private void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
