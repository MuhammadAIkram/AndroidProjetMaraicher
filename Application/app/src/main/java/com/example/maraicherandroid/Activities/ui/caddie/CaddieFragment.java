package com.example.maraicherandroid.Activities.ui.caddie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maraicherandroid.Controller.Controller;
import com.example.maraicherandroid.Modele.MyRecyclerAdapter;
import com.example.maraicherandroid.R;
import com.example.maraicherandroid.databinding.FragmentCaddieBinding;

import java.util.LinkedList;

public class CaddieFragment extends Fragment {

    private FragmentCaddieBinding binding;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CaddieViewModel dashboardViewModel =
                new ViewModelProvider(this).get(CaddieViewModel.class);

        binding = FragmentCaddieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up RecyclerView
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create and set the adapter
        adapter = new MyRecyclerAdapter(Controller.getInstance().getCaddieList());
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}