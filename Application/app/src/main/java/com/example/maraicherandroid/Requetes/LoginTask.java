package com.example.maraicherandroid.Requetes;

import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import com.example.maraicherandroid.Activities.LoginActivity;
import com.example.maraicherandroid.Controller.Controller;

public class LoginTask extends AsyncTask<String, Void, Boolean> {

    private LoginActivity activity;
    private String errorMessage;

    public LoginTask(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String username = params[0];
        String password = params[1];
        boolean isChecked = Boolean.parseBoolean(params[2]);

        try {
            // Perform login operation
            return Controller.getInstance().onLogin(username, password, isChecked);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean loginSuccessful) {
        if (loginSuccessful) {
            Controller.getInstance().startHomeActivity(activity);
            activity.finish();
        } else {
            // Show a dialog box with the error message
            showErrorDialog(errorMessage);
        }
    }

    private void showErrorDialog(String errorMessage) {
        // Use AlertDialog to show the error message
        new AlertDialog.Builder(activity)
                .setTitle("Erreur Login")
                .setMessage(errorMessage != null ? errorMessage : "Login unsuccessful")
                .setPositiveButton("OK", null)
                .show();
    }
}
