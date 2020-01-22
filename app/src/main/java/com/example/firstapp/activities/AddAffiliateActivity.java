package com.example.firstapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstapp.models.BestItemModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAffiliateActivity extends AppCompatActivity {

    EditText affTitle, affCategory, affThumbnail, affUrl, affOffer;
    private Button submitBtn;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_affiliate);
        setUpRef();

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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Affiliates");
    }

    private void writeNewAffiliate() {

        String title = affTitle.getText().toString();
        String category = affCategory.getText().toString();
        String thumbnail = affThumbnail.getText().toString();
        String url = affUrl.getText().toString();
        String offer = affOffer.getText().toString();

        BestItemModel affiliate = new BestItemModel(thumbnail, category, title, url, offer);

        if (title.isEmpty() || category.isEmpty() || thumbnail.isEmpty() || url.isEmpty() || offer.isEmpty()){
            Toast.makeText(AddAffiliateActivity.this, "Fill up all data please", Toast.LENGTH_SHORT).show();

        } else {
            mDatabase.child("offers").push().setValue(affiliate).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(AddAffiliateActivity.this, "New Data Added Successfully", Toast.LENGTH_SHORT).show();

                }
            });
        }



    }
}
