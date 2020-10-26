package com.trishasofttech.todofirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText et_task;
    ImageView iv_send;
    List<MyModel> alltask;
    DatabaseReference databaseReference;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_send = findViewById(R.id.iv_send);
        et_task = findViewById(R.id.et_task);
        recyclerView = findViewById(R.id.recyclerview);
        alltask = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*to get the database reference instance from reatime database*/
        databaseReference = FirebaseDatabase.getInstance().getReference();


        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*to add the data into MyModel and pass to databasereference*/
                MyModel myModel = new MyModel(et_task.getText().toString());
                /*to pass the value with auto generate unique key*/
                databaseReference.push().setValue(myModel);
                /*To display msg after send msg*/
                Toast.makeText(MainActivity.this, "Message Sended Successfully", Toast.LENGTH_SHORT).show();
                /*to clear the edittext*/
                et_task.setText("");
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getTask(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getTask(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    getTaskDelete(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getTaskDelete(DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
        {
            String stask = dataSnapshot1.getValue(String.class);
            for (int i=0; i<alltask.size(); i++)
            {
              //  if the delete item exists in all database list
                if (alltask.get(i).getTask().equals(stask))
                {
                    //remove the delete item list from AllTask
                    alltask.remove(i);
                }
            }
            //to refersh the recyclerview based on alltask
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, alltask);
            recyclerView.setAdapter(recyclerViewAdapter);
        }

    }

    private void getTask(DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
            String stask = dataSnapshot1.getValue(String.class);
            alltask.add(new MyModel(stask));
            /*to add the data into adapter*/
            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, alltask);
            /*to attach the adapter into recyclerview*/
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }
}