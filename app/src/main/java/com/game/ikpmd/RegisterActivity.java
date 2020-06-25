package com.game.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.game.ikpmd.firebaseConnector.FirebaseConnector;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    FirebaseConnector firebaseConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // FirebaseConnector setup
        firebaseConnector = new FirebaseConnector();
        firebaseConnector.connect();

        // Create current page listeners
        createListeners();
    }

    private void createListeners(){
        Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               registerUser();
            }
        });
    }

    private void registerUser(){
        EditText usernameField = (EditText)findViewById(R.id.registerUsernameField);
        EditText passwordField = (EditText)findViewById(R.id.registerPasswordField);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        firebaseConnector.registerUser(username, password);
        moveToLogin();
    }

    private void moveToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}