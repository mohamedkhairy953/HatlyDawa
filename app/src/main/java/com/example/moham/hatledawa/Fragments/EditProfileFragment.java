package com.example.moham.hatledawa.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.moham.hatledawa.Model.UserLab;
import com.example.moham.hatledawa.R;
import com.squareup.picasso.Picasso;

/**
 * Created by moham on 23/02/2017.
 */

public class EditProfileFragment extends android.app.DialogFragment {
    public static final String URI="uri";
    public static final String NAME="name";
    public static final String PHONE="phone";
    public static final String PASSWORD="password";
    private ImageView userImageView;
    private EditText userName;
    private  EditText  password;
    private EditText  phone;
    private AlertDialog dialog;
    private Uri imageUri;

    public static EditProfileFragment newFragment(String uri,String name,String phone,String password){
        Bundle bundle=new Bundle();
        bundle.putSerializable(URI,uri);
        bundle.putSerializable(NAME,name);
        bundle.putSerializable(PHONE,phone);
        bundle.putSerializable(PASSWORD,password);
        EditProfileFragment fragment=new EditProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View  view= LayoutInflater.from(getActivity()).inflate(R.layout.edit_profile,null);
         userImageView= (ImageView) view.findViewById(R.id.user_image_view_profile);
         userName= (EditText) view.findViewById(R.id.name_edit_profile);
         password= (EditText) view.findViewById(R.id.password_profile);
         phone= (EditText) view.findViewById(R.id.phone_number_profile);
         Picasso.with(getActivity()).load(Uri.parse((String) getArguments().getSerializable(URI))).into(userImageView);
         userName.setText((String) getArguments().getSerializable(NAME));
         password.setText((String) getArguments().getSerializable(PASSWORD));
         phone.setText((String) getArguments().getSerializable(PHONE));
        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
         dialog=new AlertDialog.Builder(getActivity())
                 .setView(view)
                 .create();
       // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setCanceledOnTouchOutside(false);
         dialog.show();
        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode== Activity.RESULT_OK){
           imageUri=data.getData();
            Picasso.with(getActivity()).load(imageUri).into(userImageView);
        }
    }

    private void save(String userName,String phone , String password , Uri imageUrl){
        if(TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone) && android.util.Patterns.PHONE.matcher(phone).matches()){

        }
    }
}
