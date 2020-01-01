package com.example.firstapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.adapters.ViewPagerAdapter;
import com.example.firstapp.classes.User;
import com.example.firstapp.fragments.BestOffersFragment;
import com.example.firstapp.fragments.CatFragment;
import com.example.firstapp.fragments.TopFragment;
import com.example.firstapp.models.BestItemModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    SliderLayout sliderLayout;
    NavigationView navigationView;
    int counter = 0;
    ImageView bottomBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;

    private TextView hName, hEmail;

    private List<BestItemModel> items;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!isConnected(this)) {
            buildDialog(this).show();
        }
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if (user == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }


        bottomBtn = findViewById(R.id.bottomArrow);

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.NONE); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :


        setSliderViews();

        Toolbar toolbar2 = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar2);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar2, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        hName = header.findViewById(R.id.headName);
        hEmail = header.findViewById(R.id.headEmail);

        String userId = firebaseAuth.getUid();
        assert userId != null;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                assert user != null;
                hName.setText(user.name);
                hEmail.setText(user.email);
                Log.w("RETRIEVED", user.name + " " + user.email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FAILED", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MainActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        viewPager = findViewById(R.id.tab_viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabActions(tabLayout, viewPager);
    }

    static void tabActions(TabLayout tabLayout, final ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.getVerticalScrollbarPosition();
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private int finish = 0;

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BestOffersFragment(), "Best Offers");
        adapter.addFrag(new CatFragment(), "Categories");
        adapter.addFrag(new TopFragment(), "Top Stories");
        viewPager.setAdapter(adapter);
    }

    public void openProfile(View v){
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }

    public void bottomArrow(View v) {
        counter++;
        if (counter%2==1) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.manage_account);
        } else {

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }


    }

    private void setSliderViews() {



        for (int i = 1; i <= 10; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);


            switch (i) {
                case 1:
                    sliderView.setImageUrl("https://i0.wp.com/metro.co.uk/wp-content/uploads/2019/03/SEI_56783050.jpg?quality=90&strip=all&zoom=1&resize=644%2C338&ssl=1");
                    break;
                case 2:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1a.png?alt=media&token=1dfc5f0e-0e9f-4983-86c4-a73b8b89cbf0");
                    break;
                case 3:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1aaaa.png?alt=media&token=89783e81-f6ed-4846-9c3e-43de8947814f");
                    break;
                case 4:
                    sliderView.setImageUrl("https://i0.wp.com/metro.co.uk/wp-content/uploads/2019/03/SEI_56783050.jpg?quality=90&strip=all&zoom=1&resize=644%2C338&ssl=1");
                    break;
                case 5:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1aaaa.png?alt=media&token=89783e81-f6ed-4846-9c3e-43de8947814f");
                    break;
                case 6:
                    sliderView.setImageUrl("https://i0.wp.com/metro.co.uk/wp-content/uploads/2019/03/SEI_56783050.jpg?quality=90&strip=all&zoom=1&resize=644%2C338&ssl=1");
                    break;
                case 7:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1a.png?alt=media&token=1dfc5f0e-0e9f-4983-86c4-a73b8b89cbf0");
                    break;
                case 8:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1aaaa.png?alt=media&token=89783e81-f6ed-4846-9c3e-43de8947814f");
                    break;
                case 9:
                    sliderView.setImageUrl("https://i0.wp.com/metro.co.uk/wp-content/uploads/2019/03/SEI_56783050.jpg?quality=90&strip=all&zoom=1&resize=644%2C338&ssl=1");
                    break;
                case 10:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1aaaa.png?alt=media&token=89783e81-f6ed-4846-9c3e-43de8947814f");
                    break;


            }

            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    startActivity(new Intent(getApplicationContext(), OfferDetailsActivity.class));
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish++;

            if(finish == 1)
            {
                Toast.makeText(MainActivity.this, "Press again to exit",Toast.LENGTH_LONG).show();
            }
            else{
                ActivityCompat.finishAffinity(MainActivity.this);
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), AddAffiliateActivity.class));

        } else if (id == R.id.nav_refer) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.bottomArrow) {

        }
        else if( id == R.id.addAff){
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }else if(id == R.id.logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Connect to mobile data or Wifi connection.");

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(getIntent());
            }
        });
        return builder;
    }
}
