package com.example.firstapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.adapters.ViewPagerAdapter;
import com.example.firstapp.classes.User;
import com.example.firstapp.fragments.ActivitiesFragment;
import com.example.firstapp.fragments.OverviewFragment;
import com.example.firstapp.fragments.TopFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private ViewPager viewPagerpro;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private TextView pName, pEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        pName = findViewById(R.id.profName);
        pEmail = findViewById(R.id.profEmail);


        String userId = firebaseAuth.getUid();
        assert userId != null;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                assert user != null;
                pName.setText(user.name);
                pEmail.setText(user.email);
                Log.w("RETRIEVED", user.name + " " + user.email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FAILED", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(ProfileActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar2 = findViewById(R.id.proToolbar);
        setSupportActionBar(toolbar2);
        toolbar2.setNavigationIcon(R.drawable.go_back);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });



        viewPagerpro = findViewById(R.id.pro_viewpager);
        if (viewPagerpro != null){
            setupViewPager(viewPagerpro);
        }


        TabLayout tabLayout = findViewById(R.id.tabLayoutPro);
        MainActivity.tabActions(tabLayout, viewPagerpro);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OverviewFragment(), "Overview");
        adapter.addFrag(new ActivitiesFragment(), "Activities");
        adapter.addFrag(new TopFragment(), "Withdrawals");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
