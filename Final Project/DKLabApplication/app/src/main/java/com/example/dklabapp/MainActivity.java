package com.example.dklabapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int LIST_RATING = 500;
    private int currentTheme;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseRecyclerAdapter<Instrument, InstrumentAdapter.InstrumentHolder> firebaseRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Query query;
    private com.example.dklabapp.Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings.setPreferences(this);
        setTheme(settings.DEFAULT_THEME);
        currentTheme =settings.DEFAULT_THEME;
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=findViewById(R.id.recyclerview); // recycler view with the desired layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // have it be a linear layout

        firebaseDatabase = FirebaseDatabase.getInstance(); // get instance to the firebase database
        query = firebaseDatabase.getReference().child("instruments").orderByChild("name");

        FirebaseRecyclerOptions<Instrument> options =
                new FirebaseRecyclerOptions.Builder<Instrument>().setQuery(query, Instrument.class).build();
        firebaseRecyclerAdapter = new InstrumentAdapter(options);

        // THIS IS WHERE THE CODE WOULD GO FOR THE FAB TO POTENTIALLY ADD AN INSTRUMENT

        FirebaseApp.initializeApp(this);

        // Singleton to see if the user is signed in
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) { // if there is no user
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(), LIST_RATING
                    );
                }
            }
        };
    }


    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
        firebaseRecyclerAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener); // add the auth state listener after being resumed
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu); // TODO: Create the menu folder in Resources and create options menu layout
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings: // TODO: Create action settings layout
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class); // TODO: Create SettingsActivity class
                startActivity(startSettingsActivity);
                return true;
            case R.id.sign_out: // TODO: Create sign_out in options menu XML file
                AuthUI.getInstance().signOut(this); // gives a menu item so sign out
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK && requestCode == LIST_RATING){
            int instrumentID = 0; // TODO: Figure out what this does

        }
    }


}
