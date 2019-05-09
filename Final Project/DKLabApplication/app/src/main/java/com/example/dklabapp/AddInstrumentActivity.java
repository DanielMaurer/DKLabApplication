package com.example.dklabapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.dklabapp.R.id.instrumentNameEditText;

public class AddInstrumentActivity extends AppCompatActivity {

    private final String TAG = AddInstrumentActivity.class.getSimpleName();
    private Instrument instrument;
    private EditText instrumentNameEditText;
    private EditText instrumentIdEditText;
    private EditText descriptionEditText;
    private EditText websiteEditText;

    private Button availableButton;
    private Button submitButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("dklabapp");

        instrument = new Instrument();

        // For Edit-Text
        instrumentNameEditText = findViewById(R.id.instrumentNameEditText);
        instrumentIdEditText = findViewById(R.id.instrumentIdEditText);
        descriptionEditText = findViewById(R.id.instrumentDescriptionEditText);
        websiteEditText = findViewById(R.id.instrumentWebsiteEditText);

        instrumentNameEditText.addTextChangedListener(new NameTextListener(instrumentNameEditText));
        instrumentIdEditText.addTextChangedListener(new NameTextListener(instrumentIdEditText));
        descriptionEditText.addTextChangedListener(new NameTextListener(descriptionEditText));
        websiteEditText.addTextChangedListener(new NameTextListener(websiteEditText));

        // for buttons
        availableButton = findViewById(R.id.availableButton);
        availableButton.setOnClickListener(new AvailableClickListener());
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new SubmitClickListener());
    }

    private class NameTextListener implements TextWatcher{
        private View editText;

        public NameTextListener(View v){
            editText = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(editText == instrumentNameEditText){
                instrument.setName(s.toString());
            } else if(editText == instrumentIdEditText){
                instrument.setInstrumentID(Integer.parseInt(s.toString()));
            } else if(editText == descriptionEditText){
                instrument.setDescription(s.toString());
            } else if(editText == websiteEditText){
                instrument.setWebsite(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class AvailableClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            boolean available = instrument.isAvaliable();
            if(available){
                instrument.setAvaliable(false);
            } else {
                instrument.setAvaliable(true);
            }
        }
    }

    private class SubmitClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String instrumentName = instrument.getName();
            String instrumentAvailable = String.valueOf(instrument.isAvaliable());

            databaseReference.push().setValue(instrument);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("instrumentName", instrumentName);
            returnIntent.putExtra("instrumentAvailable", instrumentAvailable);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }
}
