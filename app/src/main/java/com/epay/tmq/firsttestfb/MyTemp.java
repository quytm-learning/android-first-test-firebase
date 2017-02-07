package com.epay.tmq.firsttestfb;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;

import com.epay.tmq.firsttestfb.adapters.ListDataFBAdapter;
import com.epay.tmq.firsttestfb.models.User;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

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



        DatabaseReference connectedRef1 = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    System.out.println("connected");
                } else {
                    System.out.println("not connected");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });



        // Khi ket noi tu nhieu thiet bi, chung ta luu moi ket noi vao connectionRef, neu null thi Offline
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myConnectionsRef = database.getReference("users/joe/connections");

        // Luu timestamp khi bi mat ket noi (thoi gian cuoi cung khi van Online)
        final DatabaseReference lastOnlineRef = database.getReference("/users/joe/lastOnline");

        final DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    // Them thiet bi nay vao danh sach ket noi
                    // gia tri nay co the chua thong tin ve thiet bi, hoac timestamp, ...
                    DatabaseReference con = myConnectionsRef.push();
                    con.setValue(Boolean.TRUE);

                    // Khi thiet bi ngat ket noi -> remove no
                    con.onDisconnect().removeValue();

                    // Khi ngat ket noi, cap nhat timestamp: lan cuoi online
                    lastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled at .info/connected");
            }
        });


        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("news");
        RemoteMessage.Builder remoteMessage = new RemoteMessage.Builder(MainActivity.TOKEN + "@gcm.googleapis.com")
                .setMessageId("11111")
                .addData("key", "value");
        firebaseMessaging.send(remoteMessage.build());

//        Context context = getClass();
        String id = "", name = "";


//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//        FirebaseAnalytics.UserProperty


//        mFirebaseAnalytics.setUserProperty();



        reference.getDatabase();
        users.getDatabase();
    }
}
