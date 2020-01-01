package com.example.firstapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firstapp.adapters.BestOffersAdapter;
import com.example.firstapp.models.BestItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategorizedActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView catRv;
    private DatabaseReference mDatabase;

    private List<BestItemModel> filterItems;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorized);

        setupViews();

        setActionOnViews();
    }

    private void setActionOnViews() {
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        setSupportActionBar(toolbar);
        toolbar.setTitle(category);
        toolbar.setNavigationIcon(R.drawable.go_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchUsers(category);

    }

    private void setupViews() {
        swipeRefreshLayout = findViewById(R.id.swipe_rthis);
        catRv = findViewById(R.id.categorisedRv);
        toolbar = findViewById(R.id.categorisedToolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Affiliates");

    }

    private void searchUsers(final String query) {
        try{
            filterItems = new ArrayList<>();
            mDatabase.child("offers").addValueEventListener(new ValueEventListener() {
                BestItemModel aff;
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot affSnapshot: dataSnapshot.getChildren()){
                        aff =  affSnapshot.getValue(BestItemModel.class);
                        BestItemModel modelData;
                        if (aff != null && (aff.getTitle().toLowerCase().contains(query.toLowerCase())||
                                aff.getCategory().toLowerCase().contains(query.toLowerCase()))) {
                            modelData = new BestItemModel(aff.getThumbnail(), aff.getCategory(),
                                    aff.getTitle(), aff.getUrl(), aff.getOffer());
                            filterItems.add(modelData);
                        }

                        assert aff != null;
                        Log.i("LIST", aff.getTitle().contains(query.toLowerCase())? aff.getTitle():query);

                    }

                    BestOffersAdapter recyclerviewAdapter = new BestOffersAdapter(getApplicationContext(), filterItems);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    catRv.setLayoutManager(layoutManager);
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    catRv.setAdapter(recyclerviewAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Data retrieve failed", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.i("DATA", e.toString());

        }

    }

}
