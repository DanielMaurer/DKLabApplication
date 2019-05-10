package com.example.dklabapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HistoryAdapter extends FirebaseRecyclerAdapter<Instrument, HistoryAdapter.HistoryHolder> {

    class HistoryHolder extends RecyclerView.ViewHolder {

        // Text Views for the instrument name and if it is avaliable
        private final TextView userTextView;
        private final TextView dateUsedTextView;

        HistoryHolder (View itemView){
            super(itemView);
            userTextView = itemView.findViewById(R.id.userTextView);
            dateUsedTextView = itemView.findViewById(R.id.dateUsedTextView);

        }

    }

    private Context context;


    public HistoryAdapter(@NonNull FirebaseRecyclerOptions<Instrument> options){
        super(options);
    }

    // set the names of what will be in the recycler view
    @Override
    protected void onBindViewHolder(@NonNull HistoryAdapter.HistoryHolder holder, int position, @NonNull Instrument model) {
        Instrument instrument = model;
        holder.userTextView.setText(model.getName());      // Change these two values to
        holder.dateUsedTextView.setText(model.getName());  // display the user and time
    }

    // Bring the names of the history into the recycler view
    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row_history, viewGroup, false); //
        return new HistoryAdapter.HistoryHolder(view);

    }
}
