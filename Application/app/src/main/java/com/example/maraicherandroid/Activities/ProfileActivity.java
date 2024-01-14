package com.example.maraicherandroid.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.maraicherandroid.Controller.Controller;
import com.example.maraicherandroid.R;
import com.example.maraicherandroid.Requetes.LogoutTask;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void onLogout(View v){
        new LogoutTask(this).execute();
    }

    public void toggleLanguage(View view) {
        // Get the current locale
        Resources res = getResources();
        Configuration config = res.getConfiguration();

        // Toggle between English and French
        if (config.locale.getLanguage().equals("fr")) {
            config.locale = new Locale("en");
        } else {
            config.locale = new Locale("fr");
        }

        // Update the configuration and reload the UI
        res.updateConfiguration(config, res.getDisplayMetrics());
        reloadUI();
    }

    private void reloadUI() {
        finish();

        Controller.getActivity().reloadUI();
    }
}