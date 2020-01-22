package com.example.firstapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstapp.activities.R;
import com.example.firstapp.adapters.CatRecyclerViewAdapter;
import com.example.firstapp.models.BestItemModel;
import com.example.firstapp.models.CategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CatFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference dbRef;



    ArrayList<CategoryModel> categories;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cat_child_layout, container, false);
        recyclerView = rootView.findViewById(R.id.cat_recycle);

        dbRef = FirebaseDatabase.getInstance().getReference("Affiliates");

        getAllCategory();

        return rootView;

    }

    private void getAllCategory() {
        categories = new ArrayList<>();


        dbRef.child("offers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categories.clear();
                for (DataSnapshot affSnapshot : dataSnapshot.getChildren()) {
                    CategoryModel aff = affSnapshot.getValue(CategoryModel.class);

                    categories.add(aff);
                }



                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                recyclerView.setAdapter(new CatRecyclerViewAdapter(getContext(), categories));


                Log.i("ListInside", categories.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
