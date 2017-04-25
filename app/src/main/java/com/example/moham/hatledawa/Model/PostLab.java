package com.example.moham.hatledawa.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by moham on 20/02/2017.
 */
public class PostLab {
    private static PostLab mPostLab;
    private DatabaseReference  mDatabaseReference;
    private List<Post> mPosts;
    public static PostLab getInstance() {
        if (mPostLab==null){
            mPostLab=new PostLab();
        }
        return mPostLab;
    }

    private PostLab() {
        mPosts=new ArrayList<>();
        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("Posts");
    }


    public void setmPosts(Post post){
      mPosts.add(post);
    }

    public Post getPost(String uuid){
        for (Post post:mPosts) {
            if (post.getUuid().equals(uuid)){
                return post;
            }
        }
        return null;
    }
    public DatabaseReference  addPost(String phone,String posterName,String drugName,String drugType,String drugCon,String doctorNumber,String url){
        DatabaseReference mReference=mDatabaseReference.child(phone);
        String uuid=String.valueOf(UUID.randomUUID());
        DatabaseReference reference=mReference.child(uuid);
        reference.child("image").setValue(url);
        reference.child("uuid").setValue(uuid);
        reference.child("posterName").setValue(posterName);
        reference.child("drugName").setValue(drugName);
        reference.child("drugType").setValue(drugType);
        reference.child("drugCon").setValue(drugCon);
        reference.child("doctorNumber").setValue(doctorNumber);
        return reference;
    }


}