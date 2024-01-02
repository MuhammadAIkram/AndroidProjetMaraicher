package com.example.maraicherandroid.Activities;

import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.maraicherandroid.Controller.Controller;
import com.example.maraicherandroid.R;

public class ConnexionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
    }

    public void onConnexion(View v){
        EditText PortEditText = findViewById(R.id.PortField);
        EditText IPEditText = findViewById(R.id.Ipfield);

        String port = PortEditText.getText().toString();
        String ip = IPEditText.getText().toString();

        try {
            if (port.isEmpty() || ip.isEmpty()) throw new Exception("merci de renseigner le port et l'ip");

            System.out.println(port + "-----" + ip);

            //Controller.getInstance().onConnecter(ip,port);

            Controller.getInstance().startLoginActivity(this);

            finish();
        }
        catch (Exception exception) {
            Controller.getInstance().showMessage("Erreur", exception.getMessage(), this);
        }
    }
}