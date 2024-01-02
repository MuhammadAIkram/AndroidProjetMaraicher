package com.example.maraicherandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.maraicherandroid.Controller.Controller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Controller con = Controller.getInstance();
        con.startConnexionActivity(this);

        finish();
    }
}