package com.example.maraicherandroid.Requetes;

import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import com.example.maraicherandroid.Activities.ui.select.SelectFragment;
import com.example.maraicherandroid.Controller.Controller;

public class BuyTask extends AsyncTask<Void, Void, Boolean> {
    private SelectFragment fragment;
    private String errorMessage;
    private int stockSpin;

    public BuyTask(SelectFragment fragment, int stockSpin) {
        this.fragment = fragment;
        this.stockSpin = stockSpin;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            return Controller.getInstance().onAchat(stockSpin);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean AchatSuccessful) {
        if (AchatSuccessful) {
            fragment.loadArticle();

            showDialog("Achat", "Achat succes");
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
