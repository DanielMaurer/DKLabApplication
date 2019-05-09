package com.example.dklabapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class InstrumentDetailActivity extends AppCompatActivity {
    private final String TAG = InstrumentDetailActivity.class.getSimpleName();
    private Instrument instrument;
    private Button bookInstrumentButton;
    private Button viewHistoryButton;
    private Button resourcesButton;

    //private FirebaseDatabase firebaseDatabase;
    //private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_detail);

        Intent intent = getIntent();
        instrument = intent.getParcelableExtra("instrument");

        if(instrument != null){
            TextView instrumentNameTextView = findViewById(R.id.instrumentNameTextView);
            instrumentNameTextView.setText(instrument.getName());

            TextView instrumentDescriptionTextView = findViewById(R.id.instrumentDescriptionTextView);
            instrumentDescriptionTextView.setText(instrument.getDescription());
        }

        // Button to book the instrument
        bookInstrumentButton = findViewById(R.id.bookInstrumentButton);
        bookInstrumentButton.setOnClickListener(new BookInstrumentClickListener());

        // Button to view the instrument history
        viewHistoryButton = findViewById(R.id.viewHistoryButton);
        viewHistoryButton.setOnClickListener(new ViewHistoryClickListener());

        // Button to access resources for the instrument
        resourcesButton = findViewById(R.id.resourcesButton);
        resourcesButton.setOnClickListener(new ResourcesClickLister());
    }

    private class BookInstrumentClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "This instrument is now marked as in use.", Toast.LENGTH_LONG).show();
            if(instrument.isAvaliable()){
                instrument.setAvaliable(false);
            } else {
                instrument.setAvaliable(true);
            }
        }
    }

    private class ViewHistoryClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "This button will let you view the history of the instrument", Toast.LENGTH_LONG).show();
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
