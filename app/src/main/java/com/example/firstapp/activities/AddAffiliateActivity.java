package com.example.firstapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.firstapp.models.BestItemModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddAffiliateActivity extends AppCompatActivity {

    EditText affTitle, affThumbnail, affUrl, affOffer, getCate;
    AutoCompleteTextView  affCategory;
    private Button submitBtn;
    private DatabaseReference mDatabase;

    LinearLayout affLayout;

    ArrayList<String> categories = new ArrayList<>();



    String NODE = "offers";

    RadioGroup btnGroup;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_affiliate);
        setUpRef();
        categories.add("Accessories");
        categories.add("Gadgets");
        categories.add("Food");

        categories.add("Accessories");
        categories.add("Gadgets");
        categories.add("Food");

        categories.add("Accessories");
        categories.add("Gadgets");
        categories.add("Food");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,categories);

        affCategory.setAdapter(arrayAdapter);

        affCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    affCategory.showDropDown();
            }
        });



        affCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                affCategory.showDropDown();
                return false;
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewAffiliate();
             }
        });



    }



    private void setUpRef() {
        affTitle = findViewById(R.id.aff_title);
        affCategory = findViewById(R.id.aff_category);
        affThumbnail = findViewById(R.id.aff_thumbnail);
        affUrl = findViewById(R.id.aff_url);
        affOffer = findViewById(R.id.aff_offer);
        submitBtn = findViewById(R.id.submit_btn);
        btnGroup = findViewById(R.id.btnGroup);
        affLayout = findViewById(R.id.affLayout);
        getCate = findViewById(R.id.aff_get_cate);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Affiliates");
    }

    private void writeNewAffiliate() {

        String title = affTitle.getText().toString();
        String category = affCategory.getText().toString();
        String thumbnail = affThumbnail.getText().toString();
        String url = affUrl.getText().toString();
        String offer = affOffer.getText().toString();
        String get = getCate.getText().toString();

        BestItemModel affiliate = new BestItemModel(thumbnail, category, title, url, offer);

        if (title.isEmpty() || category.isEmpty() || thumbnail.isEmpty() || url.isEmpty() || offer.isEmpty()){
            Toast.makeText(AddAffiliateActivity.this, "Fill up all data please", Toast.LENGTH_SHORT).show();

        } else {

            if (NODE.equals("categories")){
                Map<String, Object> cate = new HashMap<>();

                cate.put("category", get);

                mDatabase.child(NODE).push().setValue(cate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Toast.makeText(AddAffiliateActivity.this, "New Data Added Successfully", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            mDatabase.child(NODE).push().setValue(affiliate).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(AddAffiliateActivity.this, "New Data Added Successfully", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }


    public void clickedRadioButton(View view) {
        int btnId = btnGroup.getCheckedRadioButtonId();

        switch (btnId){
            case R.id.offerBtn:
                affLayout.setVisibility(View.VISIBLE);
                getCate.setVisibility(View.GONE);
                NODE = "offers";
                break;

            case R.id.bannerBtn:
                affLayout.setVisibility(View.VISIBLE);
                getCate.setVisibility(View.GONE);
                NODE = "banners";
                break;

            case R.id.catBtn:
                affLayout.setVisibility(View.GONE);
                getCate.setVisibility(View.VISIBLE);
                NODE = "categories";
                break;
        }
    }
}
