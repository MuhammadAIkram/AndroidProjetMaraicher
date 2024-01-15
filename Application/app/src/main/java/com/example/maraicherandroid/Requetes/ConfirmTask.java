package com.example.maraicherandroid.Requetes;

import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import com.example.maraicherandroid.Activities.ui.paiement.PaiementFragment;
import com.example.maraicherandroid.Controller.Controller;

public class ConfirmTask extends AsyncTask<Void, Void, Boolean> {
    private PaiementFragment fragment;
    private String errorMessage;

    public ConfirmTask(PaiementFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            return Controller.getInstance().onConfirme();
        } catch (Exception e) {
            errorMessage = e.getMessage();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean AchatSuccessful) {
        if (AchatSuccessful) {
            fragment.updateTotale();

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