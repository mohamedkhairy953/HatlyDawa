package com.example.moham.hatledawa.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.hatledawa.Activities.LoginActivity;
import com.example.moham.hatledawa.Activities.ProfileActivity;
import com.example.moham.hatledawa.Model.UserLab;
import com.example.moham.hatledawa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

/**
 * Created by moham on 14/02/2017.
 */
 //TODO  ReGister Login
public class RegisterFragment extends Fragment {
    public static final int GALLERY_REQUEST=0;
    private EditText mNameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPassword;
    private EditText mPhoneEditText;
    private Button mRegister;
    private TextView mLogin;
    private ImageView mUserImageView;
    private Uri imageUri;
    private FirebaseAuth mFirebaseAuth;
    public static RegisterFragment newInstance(){
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view=inflater.inflate(R.layout.register_fragment,container,false);
        mNameEditText= (EditText) view.findViewById(R.id.name_edit_text);
        mPasswordEditText= (EditText) view.findViewById(R.id.password_register_text);
        mConfirmPassword= (EditText) view.findViewById(R.id.confirm_password_register_text);
        mRegister= (Button) view.findViewById(R.id.sign_up_register);
        mLogin= (TextView) view.findViewById(R.id.login_text_view_register);
        mPhoneEditText= (EditText) view.findViewById(R.id.phone_number_register);
        mUserImageView= (ImageView) view.findViewById(R.id.user_image_view_register);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST);
            }
        });
        logIn();
        register();
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Activity.RESULT_OK && requestCode==GALLERY_REQUEST){
            imageUri=data.getData();
            Picasso.with(getActivity()).load(imageUri).into(mUserImageView);
        }
    }

    private void logIn(){
      mLogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            startActivity(LoginActivity.newIntent(getActivity()));
              getActivity().finish();
          }
      });
    }

    private void register(){
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=mNameEditText.getText().toString().trim();
                String phone=mPhoneEditText.getText().toString().trim();
                String password=mPasswordEditText.getText().toString().trim();
                String confirmPassword=mConfirmPassword.getText().toString().trim();
               // Toast.makeText(getActivity(),android.util.Patterns.PHONE.matcher(phone).matches()+ "phone", Toast.LENGTH_SHORT).show();
                 Toast.makeText(getActivity(), String.valueOf(UserLab.getInstance(getActivity(),mFirebaseAuth).Register(userName,phone,password,imageUri)), Toast.LENGTH_SHORT).show();



            }
        });
    }

}
