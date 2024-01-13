package com.example.maraicherandroid.Activities.ui.paiement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaiementViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PaiementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}