package com.game.ikpmd.firebaseConnector;

import android.renderscript.Sampler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.game.ikpmd.CityActivity;
import com.game.ikpmd.LoginActivity;
import com.game.ikpmd.list.AttackListAdapter;
import com.game.ikpmd.models.Attack;
import com.game.ikpmd.models.City;
import com.game.ikpmd.models.units.Archer;
import com.game.ikpmd.models.units.Horseman;
import com.game.ikpmd.models.units.Swordsman;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Map;

public class FirebaseConnector implements Serializable {
    private FirebaseDatabase db;
    private String originalPassword;
    private int highestIdentifier;
    private City attackCity;

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

                if ((reference.getKey().equals(username)) && (originalPassword.equals(password))) {
                    loginActivity.moveToCityActivity();
                }
                originalPassword = null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

            }
        });
    }

    public void updateCityInFirebase(City city){
        DatabaseReference reference = db.getReference("cities/"+city.getUniqueIdentifier());
        reference.setValue(city);
    }

    public void createAttack(final Attack attack){
        final DatabaseReference attacksReference = db.getReference("attacks");
        final Gson gson = new Gson();

        attacksReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                highestIdentifier = 0;

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Attack loadedAttack = gson.fromJson(gson.toJson(child.getValue()), Attack.class);
                    if (loadedAttack.getUniqueAttackIdentifier() > highestIdentifier){
                        highestIdentifier = loadedAttack.getUniqueAttackIdentifier();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference = db.getReference("attacks/"+(highestIdentifier + 1));
        attack.setUniqueAttackIdentifier(highestIdentifier + 1);
        reference.setValue(attack);
    }

    public void getAttacks(final CityActivity cityActivity){
        final DatabaseReference attacksReference = db.getReference("attacks");
        final Gson gson = new Gson();

        attacksReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Attack> attacks = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Attack loadedAttack = gson.fromJson(gson.toJson(child.getValue()), Attack.class);
                    attacks.add(loadedAttack);
                }

                cityActivity.checkForAttacks(attacks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateAttackInFirebase(Attack attack){
        final DatabaseReference attackReference = db.getReference("attacks/"+attack.getUniqueAttackIdentifier());
        attackReference.setValue(attack);
    }
}
