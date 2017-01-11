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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listDataFb;
    private ListDataFBAdapter adapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
        eventDatabase();
    }

    private void initViews() {
        listDataFb = (ListView) findViewById(R.id.lv_data_fb);

    }

    private void initData(){
        adapter = new ListDataFBAdapter(this);
        listDataFb.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    private void eventDatabase() {

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
                Log.i(TAG, "onChildAdded ... " + user.username);
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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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

    private void addNewUser(User user){
        mDatabase.child(user.uid).setValue(user);
    }

    public void deleteUser(User user) {
        mDatabase.child(user.uid).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(MainActivity.this, "Delete...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
