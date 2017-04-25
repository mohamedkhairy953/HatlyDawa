package com.example.moham.hatledawa.ViewHolders;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.hatledawa.Model.Post;
import com.example.moham.hatledawa.Model.PostLab;
import com.example.moham.hatledawa.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by moham on 26/02/2017.
 */

public class DoctorPostHolder extends RecyclerView.ViewHolder {
    private Post mPost;
    private ImageView userImageView;
    private ImageView haveDruge;
    private TextView markbad;
    private TextView userName;
    private TextView  drugName;
    private TextView  drugType;
    private TextView  drugCon;
    private TextView  doctorNumber;
    private String mPhone;
    private  DatabaseReference mDatabaseReference;
    public DoctorPostHolder(View itemView) {
        super(itemView);
        userImageView= (ImageView) itemView.findViewById(R.id.poster_doctor_image_view);
        userName= (TextView) itemView.findViewById(R.id.poster_doctor_name);
        drugName= (TextView) itemView.findViewById(R.id.drug_name_doctor_post);
        drugCon= (TextView) itemView.findViewById(R.id.drug_con_doctor_post);
        drugType= (TextView) itemView.findViewById(R.id.drug_type_doctor_post);
        doctorNumber= (TextView) itemView.findViewById(R.id.number_of_doctors_doctor);
        haveDruge= (ImageView) itemView.findViewById(R.id.have_drug);
        markbad= (TextView) itemView.findViewById(R.id.mark_bad);
        markBad();
        haveDruge();
    }

    private void haveDruge(){
      haveDruge.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
           //   int doctorNumber=(mPost.getDoctorsNumber()!=null)?(Integer.parseInt(mPost.getDoctorsNumber())+1):1;
             // PostLab.getInstance().addPost(mPhone,mPost.getPosterName(),mPost.getDrugName(),mPost.getDrugType(),mPost.getDrugCon(),String.valueOf(doctorNumber));
             // PostLab.getInstance().getPost(mPost.getUuid());
              mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Posts");
              DatabaseReference mReference=mDatabaseReference.child(mPhone).child(mPost.getUuid());
              DatabaseReference reference=mDatabaseReference.child("all");
              reference.child("doctorNumber").setValue("10");
              mReference.child("doctorNumber").setValue("10");

          }
      });
    }

    private void markBad(){
        markbad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void bind(Post post , String phone, Context context){
        mPost=post;
        mPhone=phone;
       // Picasso.with(context).load(Uri.parse(post.getPosterImageUrl())).into(userImageView);
        userName.setText(post.getPosterName());
        drugName.setText(post.getDrugName());
        drugType.setText(post.getDrugType());
        drugCon.setText(post.getDrugCon());
        doctorNumber.setText(post.getDoctorsNumber());
    }
}
