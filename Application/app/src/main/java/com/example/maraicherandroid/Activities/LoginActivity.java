package com.example.maraicherandroid.Activities;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.maraicherandroid.Controller.Controller;
import com.example.maraicherandroid.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLogin(View v){
        Controller.getInstance().startHomeActivity(this);

        finish();
    }
}