package com.example.firstapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.firstapp.adapters.ViewPagerAdapter;
import com.example.firstapp.fragments.ApprovedFragment;
import com.example.firstapp.fragments.PendingFragment;
import com.example.firstapp.fragments.UsersFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPagerAdmin;
    int counter = 0;
    NavigationView proNavigationView;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        Toolbar toolbar2 = findViewById(R.id.adminToolbar);
        setSupportActionBar(toolbar2);


        proNavigationView = findViewById(R.id.nav_viewAdmin);

        DrawerLayout drawer = findViewById(R.id.drawer_layoutAdmin);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar2, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        proNavigationView.setNavigationItemSelectedListener(this);

        viewPagerAdmin = findViewById(R.id.admin_viewpager);
        if (viewPagerAdmin != null){
            setupViewPager(viewPagerAdmin);
        }


        TabLayout tabLayout = findViewById(R.id.tabLayoutAdmin);
        MainActivity.tabActions(tabLayout, viewPagerAdmin);
    }

    public void bottomArrow(View v) {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new UsersFragment(), "Users");
        adapter.addFrag(new ApprovedFragment(), "Approved");
        adapter.addFrag(new PendingFragment(), "Pending");
        adapter.addFrag(new ApprovedFragment(), "Rejected");
        viewPager.setAdapter(adapter);
    }


    int ext =0;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layoutAdmin);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else { ext++;

            if(ext == 1)
            {
                Toast.makeText(AdminPanelActivity.this, "Press again to exit",Toast.LENGTH_LONG).show();
            }
            else{
                ActivityCompat.finishAffinity(AdminPanelActivity.this);
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

        }else if(id == R.id.logout) {
             startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layoutAdmin);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
