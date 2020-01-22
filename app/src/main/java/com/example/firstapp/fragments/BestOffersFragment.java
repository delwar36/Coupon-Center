package com.example.firstapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.firstapp.activities.R;
import com.example.firstapp.adapters.BestOffersAdapter;
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
    private List<BestItemModel> items, filterItems;

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        MenuItem balnce = menu.findItem(R.id.balance);

        balnce.setTitle("\u09F3"+"20");

        SearchView searchView = (SearchView)MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                if (!TextUtils.isEmpty(s.trim())){
                    searchUsers(s);

                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            //new Viewdata().execute("https://gist.githubusercontent.com/delwar36/4c70788de39565039bbaed32c6988b99/raw/690ba2af2910be5be43d53284db2578ffa3201f7/gistfile1.txt");
                            searchUsers(s);
                        }
                    });
                } else {
                    getAffiliateList();

                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            //new Viewdata().execute("https://gist.githubusercontent.com/delwar36/4c70788de39565039bbaed32c6988b99/raw/690ba2af2910be5be43d53284db2578ffa3201f7/gistfile1.txt");
                            getAffiliateList();
                        }
                    });
                }
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    private void getAffiliateList() {
        try{
            items  = new ArrayList<>();
            mDatabase.child("offers").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    items.clear();
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

                        Log.i("LIST", aff.getTitle().contains(query.toLowerCase())? aff.getTitle():query);

                    }

                    BestOffersAdapter recyclerviewAdapter = new BestOffersAdapter(getContext(), filterItems);
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

    //
//    public class Viewdata extends Urltask {
//        @Override
//        protected void onPostExecute(List<BestItemModel> dataModels) {
//            super.onPostExecute(dataModels);
//
//            BestOffersAdapter recyclerviewAdapter = new BestOffersAdapter(getContext(), dataModels);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//            recyclerView.setLayoutManager(layoutManager);
//            if (swipeRefreshLayout.isRefreshing()) {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//            recyclerView.setAdapter(recyclerviewAdapter);
//        }
//    }


}
