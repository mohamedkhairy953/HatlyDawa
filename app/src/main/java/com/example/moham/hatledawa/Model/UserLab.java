package com.example.moham.hatledawa.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.example.moham.hatledawa.Activities.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by moham on 22/02/2017.
 */
public class UserLab {
    private Context mContext;
    private static UserLab mUserLab;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;
    private boolean flage;
    private DatabaseReference reference;
    private Map<String, String> data;

    public static UserLab getInstance(Context context, FirebaseAuth firebaseAuth) {
        if (mUserLab == null) {
            mUserLab = new UserLab(context, firebaseAuth);
        }
        return mUserLab;
    }

    private UserLab(Context context, FirebaseAuth firebaseAuth) {
        mContext = context;
        mFirebaseAuth = firebaseAuth;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mStorageReference = FirebaseStorage.getInstance().getReference().child("userImage");
        mProgressDialog = new ProgressDialog(mContext);
    }

    public boolean Register(final String userName, final String phone, final String password, final Uri uri) {
        mProgressDialog.setMessage("Register ......");
        mProgressDialog.show();
        mFirebaseAuth.createUserWithEmailAndPassword(phone + "@yahoo.com", password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        flage = false;
                        mProgressDialog.dismiss();

                    }
                });
                task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        update(userName, phone, password, uri);
                        flage = true;
                        mProgressDialog.dismiss();

                    }
                });

            }
        });

        return flage;
    }

    public void update(final String userName, final String phone, final String password, final Uri uri) {
        //  Toast.makeText(mContext, uri+"", Toast.LENGTH_SHORT).show();
        final StorageReference mReference = mStorageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference = mDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

      /*  mReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.child("imageurl").setValue(String.valueOf(taskSnapshot.getDownloadUrl()));
                mContext.startActivity(ProfileActivity.newIntent(mContext, phone, mFirebaseAuth, "user"));
                mProgressDialog.dismiss();
            }
        });*/
        reference.child("type").setValue("user");
        reference.child("username").setValue(userName);
        reference.child("phone").setValue(phone);
        reference.child("password").setValue(password);
        flage = true;
    }

    public Map<String, String> getUserData(String phone) {
        data = new ArrayMap<>();
        DatabaseReference reference = mDatabaseReference.child(phone);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = (Map<String, String>) dataSnapshot.getValue();
                Toast.makeText(mContext, data.get("imageurl") + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return data;
    }

    public boolean login(final String phone, String password) {
        mProgressDialog.setMessage("Loading .....");
        mProgressDialog.show();
        flage = false;
        mFirebaseAuth.signInWithEmailAndPassword(phone + "@yahoo.com", password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DatabaseReference mReference = mDatabaseReference.child(phone);
                    mReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                            mContext.startActivity(ProfileActivity.newIntent(mContext, phone, mFirebaseAuth, map.get("type")));
                            mProgressDialog.dismiss();
                            flage = true;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    Toast.makeText(mContext, "Login  Is fail", Toast.LENGTH_SHORT).show();
                    flage = false;
                    mProgressDialog.dismiss();
                }

            }
        });
        return flage;
    }
}
