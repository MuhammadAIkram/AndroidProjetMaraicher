package com.example.maraicherandroid.Activities.ui.paiement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.maraicherandroid.Activities.ui.select.SelectFragment;
import com.example.maraicherandroid.Controller.Controller;
import com.example.maraicherandroid.R;
import com.example.maraicherandroid.Requetes.ConfirmTask;
import com.example.maraicherandroid.Requetes.SuivanteTask;
import com.example.maraicherandroid.databinding.FragmentPaiementBinding;

public class PaiementFragment extends Fragment {

    private FragmentPaiementBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PaiementViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PaiementViewModel.class);

        binding = FragmentPaiementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView totalTextView = root.findViewById(R.id.textViewTotalCaddie);
        totalTextView.setText(String.valueOf(Controller.getInstance().getTotalCaddie()));

        Button myButtonConfirm = root.findViewById(R.id.buttonConfirm);
        myButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConfirmTask(PaiementFragment.this).execute();
            }
        });

        return root;
    }

    public void updateTotale(){
        View rootView =  binding.getRoot();
        TextView totalTextView = rootView.findViewById(R.id.textViewTotalCaddie);
        totalTextView.setText(String.valueOf(Controller.getInstance().getTotalCaddie()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}