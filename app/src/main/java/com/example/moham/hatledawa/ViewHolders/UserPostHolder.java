package com.example.moham.hatledawa.ViewHolders;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moham.hatledawa.Model.Post;
import com.example.moham.hatledawa.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by moham on 20/02/2017.
 */

public class UserPostHolder extends RecyclerView.ViewHolder {
    private ImageView userImageView;
    private TextView  userName;
    private TextView  drugName;
    private TextView  drugType;
    private TextView  drugCon;
    private TextView  doctorNumber;

    public UserPostHolder(View itemView) {
        super(itemView);
        userImageView= (ImageView) itemView.findViewById(R.id.poster_image_view);
        userName= (TextView) itemView.findViewById(R.id.poster_name);
        drugName= (TextView) itemView.findViewById(R.id.drug_name_user_post);
        drugCon= (TextView) itemView.findViewById(R.id.drug_con_user_post);
        drugType= (TextView) itemView.findViewById(R.id.drug_type_user_post);
        doctorNumber= (TextView) itemView.findViewById(R.id.number_of_doctors);
    }

    public void bind(Post post, Context context){
      //  userImageView.setImageResource(post.getPosterImageUrl());
//        Picasso.with(context).load(Uri.parse(post.getPosterImageUrl())).into(userImageView);
        userName.setText(post.getPosterName());
        drugName.setText(post.getDrugName());
        drugType.setText(post.getDrugType());
        drugCon.setText(post.getDrugCon());
        doctorNumber.setText(post.getDrugCon());
    }
}
