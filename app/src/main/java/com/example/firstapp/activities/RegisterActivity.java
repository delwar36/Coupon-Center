package com.example.firstapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity{

    private Button regButton;
    private TextView logBackTxt;
    private EditText userName, userEmail, userPassword;
    private FirebaseAuth firebaseAuth;
    String name, password, email;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupViews();
        getSupportActionBar();


        firebaseAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //Upload data to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();


                    final String fName = userName.getText().toString();
                    final String uEmail = userEmail.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Account is created",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                writeNewUser(fName, uEmail, firebaseAuth.getUid());
                                firebaseAuth.signOut();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Registration failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        logBackTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    private boolean validate() {

        boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();


        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this,"Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if (password.length()<6){
            Toast.makeText(this,"Password should be 6 characters or more", Toast.LENGTH_SHORT).show();

        } else {
            result = true;
        }
        return result;
    }

    private void setupViews(){
        userName = findViewById(R.id.etUserName);
        userEmail = findViewById(R.id.etUserEmail);
        userPassword = findViewById(R.id.etUserPassword);
        regButton = findViewById(R.id.signUpButton);
        logBackTxt = findViewById(R.id.logInback);
    }

    private void writeNewUser(String name, String email, String userId) {
        User user = new User(name, email);
        mDatabase.child("Users").child(userId).setValue(user);
    }



}
