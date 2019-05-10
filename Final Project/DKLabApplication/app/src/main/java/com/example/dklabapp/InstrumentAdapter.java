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

    class InstrumentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Text Views for the instrument name and if it is avaliable
        private final TextView instrumentNameTextView;
        private final TextView instrumentAvailableTextView;

        InstrumentHolder (View itemView){
            super(itemView);
            instrumentNameTextView = itemView.findViewById(R.id.instrumentNameTextView);
            instrumentAvailableTextView = itemView.findViewById(R.id.instrumentAvailableTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onClick(adapterPosition);
        }
    }

    private Context context;
    private final InstrumentAdapterOnClickHandler clickHandler;

    public interface InstrumentAdapterOnClickHandler {
        void onClick(int position);
    }

    public InstrumentAdapter(@NonNull FirebaseRecyclerOptions<Instrument> options, InstrumentAdapterOnClickHandler clickHandler){
        super(options);
        this.clickHandler = clickHandler;
    }

    // set the names of what will be in the recycler view
    @Override
    protected void onBindViewHolder(@NonNull InstrumentHolder holder, int position, @NonNull Instrument model) {
        Instrument instrument = model;
        holder.instrumentNameTextView.setText(model.getName());
        if(model.isAvaliable()){
            holder.instrumentAvailableTextView.setText(R.string.is_avaliable);
        } else {
            holder.instrumentAvailableTextView.setText(R.string.in_use);
        }

    }

    // Bring the names of the instruments into the recycler view
    @NonNull
    @Override
    public InstrumentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row_available, viewGroup, false); // TODO: Create list_row_available layout
        return new InstrumentAdapter.InstrumentHolder(view);

    }
}
