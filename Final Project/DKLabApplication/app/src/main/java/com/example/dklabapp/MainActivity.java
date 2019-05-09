package com.example.dklabapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements InstrumentAdapter.InstrumentAdapterOnClickHandler {
    private static final int LIST_RATING = 500;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private RecyclerView recyclerView;
    private String userId;
    private InstrumentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview); // recycler view with the desired layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // have it be a linear layout

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            userId = user.getUid();
            setAdapter();
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddInstrumentActivity.class);
                startActivity(intent);
            }
        });


        // Singleton to see if the user is signed in
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    userId = user.getUid();
                } else{
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
    public void onClick(int position) {
        Intent detailIntent = new Intent(this, InstrumentDetailActivity.class);
        detailIntent.putExtra("uid", userId); // TODO: Implement user id
        DatabaseReference ref = adapter.getRef(position);
        String id = ref.getKey();
        detailIntent.putExtra("ref", id);
        startActivity(detailIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener); // add the auth state listener after being resumed
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
            case R.id.sign_out: // TODO: Create sign_out in options menu XML file
                AuthUI.getInstance().signOut(this); // gives a menu item so sign out
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LIST_RATING){
            if(resultCode == LIST_RATING){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    userId = user.getUid();
                }
                setAdapter();
            }if(resultCode == RESULT_CANCELED){
                finish();
            }
        }
    }

    private void setAdapter(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference().child("to_do").orderByChild("uid").equalTo(userId);
        FirebaseRecyclerOptions<Instrument> options = new FirebaseRecyclerOptions.Builder<Instrument>().setQuery(query, Instrument.class).build();
        adapter = new InstrumentAdapter(options, this);
        recyclerView.setAdapter(adapter);
    }


}
