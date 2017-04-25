package com.example.moham.hatledawa.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.example.moham.hatledawa.Fragments.AddPostFragment;
import com.example.moham.hatledawa.Fragments.EditProfileFragment;
import com.example.moham.hatledawa.Fragments.LoginFragment;
import com.example.moham.hatledawa.Fragments.UpgradeFragment;
import com.example.moham.hatledawa.Fragments.UserPostFragment;
import com.example.moham.hatledawa.Model.PostLab;
import com.example.moham.hatledawa.Model.UserLab;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.hatledawa.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PHONE_KEY="com.example.moham.hatledawa.phone";
    private   FragmentManager fragmentManager=getSupportFragmentManager();
    private Fragment fragment=fragmentManager.findFragmentById(R.id.profile_container);
    private ImageView mUserImageView;
    private TextView mUserName;
    private TextView mUserPhone;
    private DatabaseReference mDatabaseReference;
    private  static FirebaseAuth mFirebaseAuth;
    private String imageUri;
    private String userName;
    private String password;
    private String phonenumber;
    private FloatingActionsMenu rightLabels;



    public static Intent newIntent(Context context,String phone, FirebaseAuth firebaseAuth,String type) {
        Intent intent=new Intent(context,ProfileActivity.class);
        intent.putExtra(PHONE_KEY,phone);
        intent.putExtra("type",type);
        mFirebaseAuth=firebaseAuth;
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view=navigationView.getHeaderView(0);
        mUserImageView= (ImageView) view.findViewById(R.id.user_nav_image_view);
        mUserName= (TextView) view.findViewById(R.id.user_name_nav);
        mUserPhone= (TextView) view.findViewById(R.id.user_phone_nav);
        UserData(getIntent().getStringExtra(PHONE_KEY));
        addFragment(UserPostFragment.newFragment(getIntent().getStringExtra(PHONE_KEY),getIntent().getStringExtra("type")));
        /***************    action       *************/
          rightLabels = (FloatingActionsMenu) findViewById(R.id.right_labels);
//        if (type.equals("user")){
//            rightLabels.setVisibility(View.VISIBLE);
//        }else{
//            rightLabels.setVisibility(View.GONE);
//        }
        FloatingActionButton addedOnce = new FloatingActionButton(this);
        addedOnce.setTitle("Add Post");
        addedOnce.setColorNormal(Color.WHITE);
        addedOnce.setImageResource(R.drawable.add_post);
        rightLabels.addButton(addedOnce);
        addedOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(ProfileActivity.this, "add your post ", Toast.LENGTH_SHORT).show();
               add_post(ProfileActivity.this);
                rightLabels.collapse();
            }
        });



    }

    private void UserData(final String phone){
    DatabaseReference mReference=mDatabaseReference.child(phone);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             Map<String,String> map= (Map<String, String>) dataSnapshot.getValue();
                Picasso.with(ProfileActivity.this).load(Uri.parse(map.get("imageurl"))).into(mUserImageView);
                imageUri=map.get("imageurl");
                userName=map.get("username");
                phonenumber=map.get("phone");
                password=map.get("password");
                Toast.makeText(ProfileActivity.this, password+"mmm", Toast.LENGTH_SHORT).show();
                mUserPhone.setText(phonenumber);
                mUserName.setText(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void edit_profile(Context context){
       android.app.FragmentManager fragmentManager=getFragmentManager();
        EditProfileFragment fragment=EditProfileFragment.newFragment(imageUri,userName,phonenumber,password);
        fragment.show(fragmentManager,"edit_fragment");
    }

    private void add_post(Context context){
     android.app.FragmentManager fragmentManager=getFragmentManager();
        AddPostFragment fragment=AddPostFragment.newFragment(imageUri,userName,phonenumber);
        fragment.show(fragmentManager,"add_post");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        switch (id){
            case R.id.edit_profile_item:
                 edit_profile(ProfileActivity.this);
                break;
            case R.id.logout:
                mFirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,DawaActivity.class));
                finish();
                break;
            case R.id.upgrade_item:
                android.app.FragmentManager fragmentManager=getFragmentManager();
                UpgradeFragment fragment=UpgradeFragment.newFragment(phonenumber,mFirebaseAuth);
                fragment.setTargetFragment(fragment,0);
                fragment.show(fragmentManager,"upgradeFragment");
//                startActivity(ProfileActivity.newIntent(this,phonenumber,mFirebaseAuth,"doctor"));
//                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addFragment(Fragment addFragment){
        fragment=addFragment;
        fragmentManager.beginTransaction().replace(R.id.profile_container,fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==RESULT_OK){
            Toast.makeText(this, data.getStringExtra(UpgradeFragment.REQUEST), Toast.LENGTH_SHORT).show();
            startActivity(ProfileActivity.newIntent(this,phonenumber,mFirebaseAuth,"doctor"));
            finish();
        }
    }
}
