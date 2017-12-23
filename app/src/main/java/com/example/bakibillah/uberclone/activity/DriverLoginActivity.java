package com.example.bakibillah.uberclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bakibillah.uberclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextPassword;
    private EditText editTextEmail;
    private Button buttonRegistrationDriver;
    private Button buttonLogindriver;

    private FirebaseAuth firebaseAuth;
    //need listener auth
    private FirebaseAuth.AuthStateListener firebaseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        setUpUIDriverLogin();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!= null){
                    Intent riderInten = new Intent(getBaseContext(),DriverMapActivity.class);
                    startActivity(riderInten);
                    finish();
                    return;
                }
            }
        };

        buttonRegistrationDriver.setOnClickListener(this);
        buttonLogindriver.setOnClickListener(this);
    }

    public void setUpUIDriverLogin(){
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonRegistrationDriver = (Button)findViewById(R.id.buttonRegistrationDriver);
        buttonLogindriver = (Button)findViewById(R.id.buttonLogindriver);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonRegistrationDriver){
            final  String email = editTextEmail.getText().toString();
            final  String password = editTextPassword.getText().toString();
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(DriverLoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(getBaseContext(),"Sign Up error",Toast.LENGTH_SHORT).show();
                    }

                    else {
                        String user_id = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance()
                                .getReference().child("Users").child("Riders").child(user_id);
                        current_user_db.setValue(true);
                    }

                }
            });
        }

        if (v.getId()== R.id.buttonLogindriver){
            final  String email = editTextEmail.getText().toString();
            final  String password = editTextPassword.getText().toString();
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(DriverLoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(getBaseContext(),"Sign In error",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseListener);
    }
}
