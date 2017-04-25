package com.example.moham.hatledawa.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moham.hatledawa.Model.Post;
import com.example.moham.hatledawa.Model.PostLab;
import com.example.moham.hatledawa.R;
import com.example.moham.hatledawa.ViewHolders.DoctorPostHolder;
import com.example.moham.hatledawa.ViewHolders.UserPostHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by moham on 20/02/2017.
 */

public class UserPostFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{
private RecyclerView mRecyclerView;
private SwipeRefreshLayout swipeRefreshLayout;
private DatabaseReference mDatabaseReference;
    private String type;

   public static UserPostFragment newFragment(String phone,String type){
           Bundle bundle=new Bundle();
           bundle.putSerializable("phone",phone);
          bundle.putSerializable("type",type);
           UserPostFragment fragment=new UserPostFragment();
           fragment.setArguments(bundle);
           return fragment;
   }

@Override
public  View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.user_postes_fragment,container,false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.user_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        type=(String) getArguments().getSerializable("type");
        UpdateUi(type);
       swipeRefreshLayout.post(new Runnable(){
@Override
public void run(){
        swipeRefreshLayout.setRefreshing(true);
        UpdateUi(type);
        }
        }
        );
        return view;
        }
@Override
public void onRefresh(){
    UpdateUi(type);
        }
public void UpdateUi(String type){
    if (type.equals("user")){
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Posts").child((String) getArguments().getSerializable("phone"));
        FirebaseRecyclerAdapter<Post,UserPostHolder> adapter=new FirebaseRecyclerAdapter<Post, UserPostHolder>
                (Post.class,R.layout.user_post,UserPostHolder.class,mDatabaseReference) {
            @Override
            protected void populateViewHolder(UserPostHolder viewHolder, Post model, int position) {
               // mDatabaseReference.child("uuid").setValue(model.getUuid());
                Toast.makeText(getActivity(), model.getPosterImageUrl()+"image", Toast.LENGTH_SHORT).show();
                viewHolder.bind(model,getActivity());
                PostLab.getInstance().setmPosts(model);
                swipeRefreshLayout.setRefreshing(false);
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }else if (type.equals("doctor")){
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Posts").child("all");
        FirebaseRecyclerAdapter<Post,DoctorPostHolder> adapter=new FirebaseRecyclerAdapter<Post, DoctorPostHolder>
                (Post.class,R.layout.doctor_post,DoctorPostHolder.class,mDatabaseReference) {
            @Override
            protected void populateViewHolder(DoctorPostHolder viewHolder, Post model, int position) {
              //  mDatabaseReference.child("uuid").setValue(model.getUuid());

                viewHolder.bind(model,(String) getArguments().getSerializable("phone"),getActivity());
                Toast.makeText(getActivity(),model.getPosterName()+ "", Toast.LENGTH_SHORT).show();
                PostLab.getInstance().setmPosts(model);
                swipeRefreshLayout.setRefreshing(false);
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


        }

@Override
public void onResume(){
        super.onResume();
        UpdateUi(type);
        Toast.makeText(getActivity(),"mohamed",Toast.LENGTH_SHORT).show();
        }

}
