package com.game.ikpmd.firebaseConnector;

import android.util.Log;

import androidx.annotation.NonNull;

import com.game.ikpmd.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class FirebaseConnector implements Serializable {
    private FirebaseDatabase db;
    private String originalPassword;

    public void connect(){
        db = FirebaseDatabase.getInstance();
    }

    public void registerUser(String username, String password){
        DatabaseReference reference = db.getReference("users/"+username);
        reference.setValue(password);
    }

    public void loginUser(final String username, final String password, final LoginActivity loginActivity){
        final DatabaseReference reference = db.getReference("users/"+username);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                originalPassword = (String) dataSnapshot.getValue();
                Log.d("hier zooi", "zooi:"+originalPassword);

                if ((reference.getKey().equals(username)) && (originalPassword.equals(password))) {
                    loginActivity.moveToCityActivity();
                }
                originalPassword = null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
<<<<<<< Updated upstream
                // helemaal niks
=======

            }
        });
    }

    public void checkCitiesForUser(final String name, final CityActivity cityActivity){
        final DatabaseReference reference = db.getReference("cities");
        final Gson gson = new Gson();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<City> cities = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    City city = gson.fromJson(gson.toJson(child.getValue()), City.class);
                    city.setUniqueIdentifier(child.getKey());
                    cities.add(city);
                }

                cityActivity.checkIfUserHasCity(name, cities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

>>>>>>> Stashed changes
            }
        });
    }

    public void updateCityInFirebase(City city){
        DatabaseReference reference = db.getReference("cities/"+city.getUniqueIdentifier());
        reference.setValue(city);
    }
}
