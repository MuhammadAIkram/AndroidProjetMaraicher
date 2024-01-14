package com.example.maraicherandroid.Requetes;

import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import com.example.maraicherandroid.Activities.ProfileActivity;
import com.example.maraicherandroid.Controller.Controller;

public class LogoutTask extends AsyncTask<String, Void, Boolean> {
    private ProfileActivity activity;
    private String errorMessage;

    public LogoutTask(ProfileActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            return Controller.getInstance().onLogout();
        } catch (Exception e) {
            errorMessage = e.getMessage();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean loginSuccessful) {
        if (loginSuccessful) {
            Controller.getInstance().startLoginActivity(activity);
            activity.finish();
        } else {
            showErrorDialog(errorMessage);
        }
    }

    private void showErrorDialog(String errorMessage) {
        new AlertDialog.Builder(activity)
                .setTitle("Erreur Login")
                .setMessage(errorMessage != null ? errorMessage : "Login unsuccessful")
                .setPositiveButton("OK", null)
                .show();
    }
}
