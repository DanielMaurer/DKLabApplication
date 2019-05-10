package com.example.dklabapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InstrumentDetailActivity extends AppCompatActivity {
    private Instrument instrument;
    private TextView instrumentName;
    private TextView instrumentDescription;
    private Button bookInstrumentButton;
    private Button viewHistoryButton;
    private Button resourcesButton;
    private String userId;
    private String ref;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_detail);

        instrumentName = findViewById(R.id.instrumentNameTextView);
        instrumentDescription = findViewById(R.id.instrumentDescriptionTextView);


        // Button to book the instrument
        bookInstrumentButton = findViewById(R.id.bookInstrumentButton);
        bookInstrumentButton.setOnClickListener(new BookInstrumentClickListener());

        // Button to view the instrument history
        viewHistoryButton = findViewById(R.id.viewHistoryButton);
        viewHistoryButton.setOnClickListener(new ViewHistoryClickListener());

        // Button to access resources for the instrument
        resourcesButton = findViewById(R.id.resourcesButton);
        resourcesButton.setOnClickListener(new ResourcesClickLister());

        // Just like the todo firebase app
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = getIntent().getStringExtra("ref");

        if(ref != null){
            databaseReference = firebaseDatabase.getReference().child("instruments").child(ref);
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    instrument = dataSnapshot.getValue(Instrument.class);
                    setUi();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            databaseReference.addValueEventListener(valueEventListener);
        } else {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("instruments");
        }
    }

    private void setUi(){
        if(instrument != null){
            instrumentName.setText(instrument.getName());
            instrumentDescription.setText(instrument.getDescription());

        }
    }

    private class BookInstrumentClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(instrument.isAvaliable()){
                instrument.setAvaliable(false);
                Toast.makeText(getApplicationContext(), "This instrument is now marked as in use.", Toast.LENGTH_LONG).show();
            } else {
                instrument.setAvaliable(true);
                Toast.makeText(getApplicationContext(), "This instrument is now marked as available.", Toast.LENGTH_LONG).show();
            }
            databaseReference.setValue(instrument);
        }
    }

    private class ViewHistoryClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "This button will let you view the history of the instrument", Toast.LENGTH_LONG).show();
            Intent historyIntent = new Intent(getApplicationContext(), InstrumentHistoryActivity.class);
            startActivity(historyIntent);


        }
    }

    private class ResourcesClickLister implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(instrument.getWebsite().equals("N/A")){
                Toast.makeText(getApplicationContext(), "No website is available for this instrument.", Toast.LENGTH_LONG).show();
            } else {
                Uri uri = Uri.parse(instrument.getWebsite());
                Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                if(myIntent.resolveActivity(getPackageManager())!=null) {
                    startActivity(myIntent);
                }
            }
        }
    }
}
