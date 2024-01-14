package com.example.maraicherandroid.Requetes;

import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import com.example.maraicherandroid.Activities.ui.select.SelectFragment;
import com.example.maraicherandroid.Controller.Controller;

public class SuivanteTask extends AsyncTask<Void, Void, Boolean> {
    private SelectFragment fragment;
    private String errorMessage;

    public SuivanteTask(SelectFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            return Controller.getInstance().onSuivante();
        } catch (Exception e) {
            errorMessage = e.getMessage();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean logoutSuccessful) {
        if (logoutSuccessful) {
            fragment.loadArticle();
        } else {
            showErrorDialog(errorMessage);
        }
    }

    private void showErrorDialog(String errorMessage) {
        if (fragment.isAdded() && fragment.getContext() != null) {
            new AlertDialog.Builder(fragment.getContext())
                    .setTitle("Erreur")
                    .setMessage(errorMessage != null ? errorMessage : "probleme avec suivante")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}