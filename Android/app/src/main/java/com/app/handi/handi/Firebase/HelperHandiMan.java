package com.app.handi.handi.Firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.handi.handi.Activitys.HandiManSignupActivity;
import com.app.handi.handi.DataTypes.HandimanData;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HelperHandiMan {
    DatabaseReference db;
    Boolean saved;
    ArrayList<HandimanData> HandiMen = new ArrayList<>();

    public HelperHandiMan(){}
    public HelperHandiMan(DatabaseReference db){
        this.db =  db;
    }

    public Boolean save(HandimanData handimanData, FirebaseUser user){
        if(handimanData==null)
            saved = false;
        else{
            try{
                db.child("HandiMen").child(handimanData.profession).child(user.getUid()).setValue(handimanData);
                saved = true;
            }catch(DatabaseException e){
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        HandiMen.clear();
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//        for(DataSnapshot ds : dataSnapshot.getChildren()){
//            HandimanData handiman = ds.getValue(HandimanData.class);
//            HandiMen.add(handiman);
//        }
        for(DataSnapshot child : children){
            HandimanData handiman = child.getValue(HandimanData.class);
            HandiMen.add(handiman);
        }
    }
    public ArrayList<HandimanData> retrieve(String profession){
        db.child("HandiMen").child(profession).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return HandiMen;
    }
}