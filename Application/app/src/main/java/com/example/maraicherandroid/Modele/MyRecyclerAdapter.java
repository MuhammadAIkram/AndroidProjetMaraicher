package com.example.maraicherandroid.Modele;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maraicherandroid.R;

import java.util.LinkedList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private LinkedList<Article> dataList;
    private int selectedRow = RecyclerView.NO_POSITION; // Initialize with no selection

    public MyRecyclerAdapter(LinkedList<Article> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article currentItem = dataList.get(position);

        // Bind only the relevant values to the views
        holder.textViewName.setText(currentItem.getIntitule());
        holder.textViewPrice.setText(String.valueOf(currentItem.getPrix()));
        holder.textViewStock.setText(String.valueOf(currentItem.getStock()));

        // Highlight the selected row
        if (position == selectedRow) {
            holder.rowLayout.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.rowLayout.setBackgroundColor(Color.WHITE);
        }

        // Set a click listener for the entire row
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousSelectedRow = selectedRow;
                selectedRow = holder.getAdapterPosition();

                notifyItemChanged(previousSelectedRow);
                notifyItemChanged(selectedRow);

                Toast.makeText(v.getContext(), "Row selected: " + currentItem.getIntitule(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rowLayout;
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowLayout = itemView.findViewById(R.id.rowLayout);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewStock = itemView.findViewById(R.id.textViewStock);
        }
    }

    // Method to get the selected row number
    public int getSelectedRow() {
        return selectedRow;
    }
}
