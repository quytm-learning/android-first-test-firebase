package com.epay.tmq.firsttestfb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.epay.tmq.firsttestfb.adapters.ListDataFBAdapter;
import com.epay.tmq.firsttestfb.models.User;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TOKEN = "drFdQputDao:APA91bHfXSMubR-TYU_89SGcNsTCr-JhU-2anmJ0MDxGXPHQeY8NotbUtIoUI46qs5zLS1ca1NhQ8b3RROmK6AvJpr4HNKiQvdxOuUzCqqOj_tw9k8wvoTrXEz-HtzImfea8_CF0QD5m";
    private ListView listDataFb;
    private ListDataFBAdapter adapter;
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
        eventDatabase();
        other();
        analytics();
        reporter();
    }

    private void initViews() {
        listDataFb = (ListView) findViewById(R.id.lv_data_fb);

    }

    private void initData() {
        adapter = new ListDataFBAdapter(this);
        listDataFb.setAdapter(adapter);

        if (db == null) {
            db = FirebaseDatabase.getInstance();
//            db.setPersistenceEnabled(true);
        }
        mDatabase = db.getReference().child("users");
    }

    private void eventDatabase() {
//        User user = new User("1", "quytm_58", "quytm_58@vnu.edu.vn");
//        mDatabase.push().setValue(user);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange ... ");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    Log.i(TAG, user.toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Error: " + databaseError.toString());
            }
        });

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                Log.i(TAG, "onChildAdded ... " + user.username + ", key = " + dataSnapshot.getKey() + ", previous = " + s);
                adapter.addItem(user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "onChildChanged ... ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onChildRemoved ... ");

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                Log.i(TAG, "onChildMoved ... " + user.username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCanceled ... (ChildEvent)");
            }

        });


    }

    private void other() {
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("news");
        RemoteMessage.Builder remoteMessage = new RemoteMessage.Builder(MainActivity.TOKEN + "@gcm.googleapis.com")
                .setMessageId("11111")
                .addData("key", "value");
        firebaseMessaging.send(remoteMessage.build());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_add_item:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                View layout = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null);
                final EditText edtId = (EditText) layout.findViewById(R.id.edt_item_id);
                final EditText edtUsername = (EditText) layout.findViewById(R.id.edt_item_username);
                final EditText edtEmail = (EditText) layout.findViewById(R.id.edt_item_email);

                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = new User(edtId.getText().toString(), edtUsername.getText().toString(), edtEmail.getText().toString());
                        addNewUser(user);
//                        addNewUserPush(user);
                    }
                });
                dialog.setNegativeButton("Cancel", null);
                dialog.setView(layout);
                dialog.show();
                break;
        }
        return true;
    }

    // ----------------- Utilities

    private void addNewUser(User user) {
        mDatabase.child(user.uid).setValue(user);
    }

    private void addNewUserPush(User user) {
        mDatabase.child(user.uid).push().setValue(user);
    }

    public void deleteUser(User user) {
        mDatabase.child(user.uid).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(MainActivity.this, "Delete...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ------------------ Analytics ----
    private void analytics() {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);

        analytics.setAnalyticsCollectionEnabled(true);
        analytics.setUserId("user id");

        analytics.setUserProperty("favorite_food", "Ga ran");
        analytics.setUserProperty("favorite_food", "Com rang");
        analytics.setUserProperty("Job", "Cai win dao");
        analytics.setUserProperty("Age", "23");
        analytics.setUserProperty("Gender", "male");
        analytics.setUserProperty("abc", "def");



        // Event select content
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "111");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "item 111");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "box");
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        // Event purchase
        Bundle bPurchase = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.COUPON, "Phieu 1");
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "VND");
        bundle.putString(FirebaseAnalytics.Param.VALUE, "100000");
        bundle.putString(FirebaseAnalytics.Param.TAX, "10%");
        bundle.putString(FirebaseAnalytics.Param.SHIPPING, "10000");
        bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID, "trans_id_11111");
        analytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bPurchase);

    }

    // ---------------- Reporter

    private void reporter(){
        FirebaseCrash.log("Activity created");
        FirebaseCrash.report(new Throwable("Error"));
    }

}
