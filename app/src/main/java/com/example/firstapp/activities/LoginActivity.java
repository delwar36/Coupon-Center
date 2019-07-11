package com.example.firstapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity{

    Button mLogin;
    TextView mReg;
    EditText mUser, mPass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseUser user;
    private String userName, userPass;

    int exit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUser = findViewById(R.id.userEmail);
        mPass = findViewById(R.id.password);
        mReg = findViewById(R.id.signUpText);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        mLogin = findViewById(R.id.login);
        mLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validate()==1){
                    progressDialog.setMessage("Logging In...");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(userName, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else if (validate() == 2){

                    progressDialog.setMessage("Logging In...");
                    progressDialog.show();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AdminPanelActivity.class));
                }
            }
        });
        mReg = findViewById(R.id.signUpText);
        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

        exit++;

        if(exit == 1)
        {
            Toast.makeText(LoginActivity.this, "Press again to exit",Toast.LENGTH_LONG).show();
        }
        else{
            ActivityCompat.finishAffinity(LoginActivity.this);
        }

    }

    public int validate(){
        int result = 0;

        userName = mUser.getText().toString();
        userPass = mPass.getText().toString();

        MainActivity main = new MainActivity();

        if(userName.isEmpty() || userPass.isEmpty()){
            Toast.makeText(this,"Please enter all the details", Toast.LENGTH_SHORT).show();
        }else if (userName.equalsIgnoreCase("Admin") && userPass.equalsIgnoreCase("Admin")){
            result = 2;
        }else if (userPass.length()<6) {
            Toast.makeText(this, "Password should be 6 characters or more", Toast.LENGTH_SHORT).show();
        } else if (!main.isConnected(this)) {
            main.buildDialog(this).show();

        } else {
            result = 1;
        }
        return result;
    }
}
