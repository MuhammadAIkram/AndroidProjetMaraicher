package com.example.maraicherandroid.Activities.ui.select;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.maraicherandroid.Controller.Controller;
import com.example.maraicherandroid.R;
import com.example.maraicherandroid.Requetes.PrecedentTask;
import com.example.maraicherandroid.Requetes.SuivanteTask;
import com.example.maraicherandroid.databinding.FragmentSelectBinding;

import java.io.IOException;
import java.io.InputStream;

public class SelectFragment extends Fragment {

    private FragmentSelectBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SelectViewModel homeViewModel =
                new ViewModelProvider(this).get(SelectViewModel.class);

        binding = FragmentSelectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button myButtonSuivante = root.findViewById(R.id.buttonSuivante);
        myButtonSuivante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SuivanteTask(SelectFragment.this).execute();
            }
        });

        Button myButtonPrecedent = root.findViewById(R.id.buttonPrecedent);
        myButtonPrecedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrecedentTask(SelectFragment.this).execute();
            }
        });

        Button myButtonbuy = root.findViewById(R.id.buttonAchat);
        myButtonbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello");
            }
        });

        loadArticle();

        return root;
    }

    public void loadArticle() {
        AssetManager assetManager = getContext().getAssets();
        try {
            //pour image
            InputStream inputStream = assetManager.open("images/" + Controller.getInstance().articleCourant.getImage());
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            View rootView =  binding.getRoot();
            ImageView imageView = rootView.findViewById(R.id.imageView);
            imageView.setImageDrawable(drawable);

            //pour les textview
            TextView articleTextView = rootView.findViewById(R.id.article);
            TextView prixunitaireTextView = rootView.findViewById(R.id.prixunitaire);
            TextView stockTextView = rootView.findViewById(R.id.stock);

            articleTextView.setText(Controller.getInstance().articleCourant.getIntitule());
            prixunitaireTextView.setText(String.valueOf(Controller.getInstance().articleCourant.getPrix()));
            stockTextView.setText(String.valueOf(Controller.getInstance().articleCourant.getStock()));

            //pour le number picker
            NumberPicker numberPicker = rootView.findViewById(R.id.numberPicker);

            // Set the minimum and maximum values
            if(Controller.getInstance().articleCourant.getStock() != 0)
                numberPicker.setMinValue(1);
            else numberPicker.setMinValue(0);

            numberPicker.setMaxValue(Controller.getInstance().articleCourant.getStock());

            // Set the initial value
            if(Controller.getInstance().articleCourant.getStock() != 0)
                numberPicker.setValue(1);
            else numberPicker.setValue(0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}