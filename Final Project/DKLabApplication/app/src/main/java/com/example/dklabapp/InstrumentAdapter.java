package com.example.dklabapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class InstrumentAdapter extends FirebaseRecyclerAdapter<Instrument, InstrumentAdapter.InstrumentHolder> {

    class InstrumentHolder extends RecyclerView.ViewHolder{

        // Text Views for the instrument name and if it is avaliable
        private final TextView instrumentNameTextView;
        private final TextView instrumentAvaliableTextView;

        InstrumentHolder (View itemView){
            super(itemView);
            instrumentNameTextView = itemView.findViewById(R.id.instrumentNameTextView);
            instrumentAvaliableTextView = itemView.findViewById(R.id.instrumentAvaliableTextView);
        }
    }

    private Context context;

    public InstrumentAdapter(@NonNull FirebaseRecyclerOptions<Instrument> options){
        super(options);
    }

    // set the names of what will be in the recycler view
    @Override
    protected void onBindViewHolder(@NonNull InstrumentHolder holder, int position, @NonNull Instrument model) {
        holder.instrumentNameTextView.setText(model.getName());
        if(model.isAvaliable()){
            holder.instrumentAvaliableTextView.setText(R.string.is_avaliable);
        } else {
            holder.instrumentAvaliableTextView.setText(R.string.in_use);
        }

    }

    // Bring the names of the instruments into the recycler view
    @NonNull
    @Override
    public InstrumentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row, viewGroup, false); // TODO: Create list_row layout
        return new InstrumentAdapter.InstrumentHolder(view);

    }
}
