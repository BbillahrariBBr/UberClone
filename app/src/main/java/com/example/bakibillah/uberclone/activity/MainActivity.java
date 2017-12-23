package com.example.bakibillah.uberclone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bakibillah.uberclone.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRiders;
    private Button buttonCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUiMainActivity();
        buttonRiders.setOnClickListener(this);
        buttonCustomers.setOnClickListener(this);
    }

    public void setUpUiMainActivity(){
        buttonRiders = (Button)findViewById(R.id.buttonRiders);
        buttonCustomers = (Button)findViewById(R.id.buttonCustomers);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.buttonRiders){
            Intent riderInten = new Intent(MainActivity.this,DriverLoginActivity.class);
            startActivity(riderInten);
            finish();
            return;
        }
       if (v.getId()== R.id.buttonCustomers){
            Intent customIntent = new Intent(MainActivity.this,CustomerLoginActivity.class);
            startActivity(customIntent);
            finish();
            return;
       }
    }
}
