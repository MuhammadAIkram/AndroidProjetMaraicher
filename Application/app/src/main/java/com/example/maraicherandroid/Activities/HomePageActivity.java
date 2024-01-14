package com.example.maraicherandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.maraicherandroid.Controller.Controller;
import com.example.maraicherandroid.R;
import com.example.maraicherandroid.databinding.ActivityHomePageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomePageActivity extends AppCompatActivity {

    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_select, R.id.navigation_caddie, R.id.navigation_paiement)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_page);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Controller.getInstance().SetHome(this);
    }

    public void onProfile(View v){
        Controller.getInstance().startPofileActivity(this);
    }

    public void reloadUI() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}