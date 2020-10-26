package com.trishasofttech.todofirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
List<MyModel> alltask;
Context context;

    public RecyclerViewAdapter( Context context,List<MyModel> alltask) {
        this.alltask = alltask;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        RecyclerViewHolders recyclerViewHolders = new RecyclerViewHolders(v, alltask);
        return recyclerViewHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolders holder, final int position) {
        holder.tv_task.setText(alltask.get(position).getTask());
        holder.tv_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                final Query query = databaseReference.orderByChild("task").equalTo(alltask.get(position).getTask());
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
        });
    }

    @Override
    public int getItemCount() {
        return alltask.size();
    }
}
