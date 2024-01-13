package com.example.maraicherandroid;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.maraicherandroid.Controller.Controller;

import java.io.InputStream;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // verifie si on peut se connecter au reseau
        if (isNetworkAvailable()) {
            // si le reseau est disponible on va cree un nouveau thread pour gerer la connection
            new Thread(this::connectToServer).start();
        }
    }

    private void connectToServer() {
        Controller con = Controller.getInstance();

        try {
            Properties properties = new Properties();
            AssetManager assetManager = getApplicationContext().getAssets();
            InputStream inputStream = assetManager.open("config.properties");

            properties.load(inputStream);

            String port = properties.getProperty("PORT");
            String IP = properties.getProperty("IP_Database");

            inputStream.close();

            con.onConnecter(IP, port);
        } catch (Exception e) {
            con.showMessage("Erreur Connexion", "Erreur avec la connexion au DB", this);
        }

        //quand la connection est etablie avec la VM il va lancer le login activity
        runOnUiThread(() -> {
            con.startLoginActivity(MainActivity.this);
            finish();
        });
    }

    //methode pour verifier si le reseau est disponible ou pas
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        return false;
    }
}
