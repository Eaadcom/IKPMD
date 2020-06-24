package com.game.ikpmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.game.ikpmd.firebaseConnector.FirebaseConnector;

public class LoginActivity extends AppCompatActivity {
    FirebaseConnector firebaseConnector;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // FirebaseConnector setup
        firebaseConnector = new FirebaseConnector();
        firebaseConnector.connect();

        // Create current page listeners
        createListeners();
    }

    private void createListeners(){
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser(){
        EditText usernameField = (EditText)findViewById(R.id.loginUsernameField);
        EditText passwordField = (EditText)findViewById(R.id.loginPasswordField);
        username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        firebaseConnector.loginUser(username, password, this);
    }

    public void moveToCityActivity(){
        Intent intent = new Intent(LoginActivity.this, CityActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}