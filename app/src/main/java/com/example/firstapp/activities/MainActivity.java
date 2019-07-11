package com.example.firstapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.firstapp.fragments.BestOffersFragment;
import com.example.firstapp.fragments.CatFragment;
import com.example.firstapp.fragments.TopFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();



        user = firebaseAuth.getCurrentUser();
        if(user == null){
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


        viewPager = findViewById(R.id.tab_viewpager);
        if (viewPager != null){
            setupViewPager(viewPager);
        }


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabActions(tabLayout, viewPager);
    }

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


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }

    private void setSliderViews() {

        for (int i = 1; i <= 6; i++) {


            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i) {
                case 1:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1aa.png?alt=media&token=d951a21f-2e0c-407d-a99e-b58ce3ca668a");
                    break;
                case 2:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1a.png?alt=media&token=1dfc5f0e-0e9f-4983-86c4-a73b8b89cbf0");
                    break;
                case 3:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1aaaa.png?alt=media&token=89783e81-f6ed-4846-9c3e-43de8947814f");
                    break;
                case 4:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1aaaaa.png?alt=media&token=05a59df4-3376-45c5-b4f2-00798d3a71e2");
                    break;
                case 5:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1aaaa.png?alt=media&token=89783e81-f6ed-4846-9c3e-43de8947814f");
                    break;

                case 6:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/omnibit-772ae.appspot.com/o/1aaaaaaaaa.png?alt=media&token=570265c1-035c-495d-beb5-1dae84158ae2");
                    break;
            }

            final int finalI = i;
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

    int finish =0;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        } else if (id == R.id.nav_refer) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.bottomArrow) {

        }
        else if( id == R.id.addAcc){
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
}
