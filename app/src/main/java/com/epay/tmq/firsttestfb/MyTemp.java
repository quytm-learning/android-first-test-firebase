package com.epay.tmq.firsttestfb;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.epay.tmq.firsttestfb.adapters.ListDataFBAdapter;
import com.epay.tmq.firsttestfb.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by tmq on 13/01/2017.
 */

public class MyTemp {
    public MyTemp(){

        final Context c = null;
        final ListDataFBAdapter adapter = new ListDataFBAdapter(c);



        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference reference = mDatabase.getReference();
        DatabaseReference users = mDatabase.getReference("users");

        reference.setValue("Hello!");

        User user = new User("id", "username", "email");
        users.setValue(user);

        users.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                // Todo...
            }
        });



        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Todo
            }
        });

        users.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                adapter.addItem(user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        reference.getDatabase();
        users.getDatabase();
    }
}
