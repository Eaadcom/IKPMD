package com.game.ikpmd.firebaseConnector;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class FirebaseConnector {
    private static FirebaseDatabase db;

    public static void connect(){
        db = FirebaseDatabase.getInstance();
    }

    public static void registerUser(String username, String password){
        DatabaseReference reference = db.getReference("users/"+username);
        reference.setValue(password);
    }
}
