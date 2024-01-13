package com.example.maraicherandroid.Activities;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.maraicherandroid.Controller.Controller;
import com.example.maraicherandroid.R;
import com.example.maraicherandroid.Requetes.LoginTask;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLogin(View v) {
        try {
            EditText usernameEditText = findViewById(R.id.Username);
            EditText passwordEditText = findViewById(R.id.Password);

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                throw new Exception("Merci de renseigner le nom d'utilisateur et le mot de passe");
            }

            System.out.println(username + password);

            CheckBox checkBox = findViewById(R.id.checkBox);
            boolean isChecked = checkBox.isChecked();

            // Perform login asynchronously
            new LoginTask(this).execute(username, password, String.valueOf(isChecked));
        } catch (Exception e) {
            Controller.getInstance().showMessage("Erreur Login", e.getMessage(), this);
        }
    }
}