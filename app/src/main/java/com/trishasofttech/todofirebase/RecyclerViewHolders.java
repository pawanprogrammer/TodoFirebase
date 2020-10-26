package com.trishasofttech.todofirebase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecyclerViewHolders extends RecyclerView.ViewHolder {
    TextView tv_task;
    ImageView iv_delete;
    List<MyModel> alltask;
    public RecyclerViewHolders(@NonNull View v, final List<MyModel> alltask) {
        super(v);
        this.alltask = alltask;
        tv_task = v.findViewById(R.id.tv_task);
        iv_delete = v.findViewById(R.id.iv_delete);

        /*to click on the iv_delete icon*/
        /*iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stask = alltask.get(getAdapterPosition()).getTask();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                final Query query = databaseReference.orderByChild("task").equalTo(stask);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                        {
                            dataSnapshot1.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });*/
    }
}
