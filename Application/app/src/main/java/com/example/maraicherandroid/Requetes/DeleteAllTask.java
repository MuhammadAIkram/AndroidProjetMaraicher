package com.example.maraicherandroid.Requetes;

import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import com.example.maraicherandroid.Activities.ui.caddie.CaddieFragment;
import com.example.maraicherandroid.Controller.Controller;

public class DeleteAllTask extends AsyncTask<Void, Void, Boolean> {
    private CaddieFragment fragment;
    private String errorMessage;

    public DeleteAllTask(CaddieFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            return Controller.getInstance().onVider();
        } catch (Exception e) {
            errorMessage = e.getMessage();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean AchatSuccessful) {
        if (AchatSuccessful) {
            fragment.updateList();

            showDialog("Vider", "Vider succes");
        } else {
            showDialog("Erreur",errorMessage);
        }
    }

    private void showDialog(String title, String errorMessage) {
        if (fragment.isAdded() && fragment.getContext() != null) {
            new AlertDialog.Builder(fragment.getContext())
                    .setTitle(title)
                    .setMessage(errorMessage != null ? errorMessage : "probleme avec suivante")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}