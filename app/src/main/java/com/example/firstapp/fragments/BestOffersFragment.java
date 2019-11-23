package com.example.firstapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.firstapp.activities.R;
import com.example.firstapp.adapters.BestOffersAdapter;
import com.example.firstapp.fetchdata.Urltask;
import com.example.firstapp.models.BestItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BestOffersFragment extends Fragment {
    RecyclerView recyclerView;

    public SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseReference mDatabase;
    private List<BestItemModel> items;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.best_recycle, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView1);
        //new Viewdata().execute("https://gist.githubusercontent.com/delwar36/4c70788de39565039bbaed32c6988b99/raw/690ba2af2910be5be43d53284db2578ffa3201f7/gistfile1.txt");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Affiliates");

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);

        getAffiliateList();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //new Viewdata().execute("https://gist.githubusercontent.com/delwar36/4c70788de39565039bbaed32c6988b99/raw/690ba2af2910be5be43d53284db2578ffa3201f7/gistfile1.txt");
                getAffiliateList();
            }
        });

        return rootView;

    }

    private void getAffiliateList() {
        try{
            items  = new ArrayList<>();
            mDatabase.child("offers").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot affSnapshot: dataSnapshot.getChildren()){
                        BestItemModel aff =  affSnapshot.getValue(BestItemModel.class);
                        BestItemModel modelData = null;
                        if (aff != null) {
                            modelData = new BestItemModel(aff.getThumbnail(), aff.getCategory(),
                                    aff.getTitle(), aff.getUrl(), aff.getOffer());
                        }
                        items.add(modelData);

                    }
                    BestOffersAdapter recyclerviewAdapter = new BestOffersAdapter(getContext(), items);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                      if (swipeRefreshLayout.isRefreshing()) {
                          swipeRefreshLayout.setRefreshing(false);
                      }
                    recyclerView.setAdapter(recyclerviewAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Data retrieve failed", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.i("DATA", e.toString());

        }

    }


    public class Viewdata extends Urltask {
        @Override
        protected void onPostExecute(List<BestItemModel> dataModels) {
            super.onPostExecute(dataModels);

            BestOffersAdapter recyclerviewAdapter = new BestOffersAdapter(getContext(), dataModels);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            recyclerView.setAdapter(recyclerviewAdapter);
        }
    }


}
