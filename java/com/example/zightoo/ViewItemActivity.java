package com.example.zightoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ViewItemActivity extends AppCompatActivity {

    private RecyclerView itemView;
    private RecyclerView.Adapter adapter;
    private List<listitem> listItems;
    private List<listitem> listItems2;
    private DatabaseReference dataRef;
    private List<String>  devID = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        itemView = (RecyclerView) findViewById(R.id.itemView);
        itemView.setHasFixedSize(true);
        itemView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        listItems2 = new ArrayList<>();

        final Query dataRef2 = FirebaseDatabase.getInstance().getReference("Devices")
                .orderByChild(FirebaseAuth.getInstance().getUid()).equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        dataRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    devID.add(snapshot.getKey());
                }
                dataRef = FirebaseDatabase.getInstance().getReference("sightings").child(incomingIntent().toLowerCase());

                dataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                listitem item = snapshot.getValue(listitem.class);
                                for(String i:devID) {
                                    if (item.getIs_public().equals("True") || item.getDevice_ID().equals(i)) {

                                        listItems.add(item);

                                        }
                                    }
                                for(listitem j:listItems){
                                    if(!listItems2.contains(j)){
                                        listItems2.add(j);
                                    }
                                }
                            }
                            adapter = new adapter(ViewItemActivity.this, listItems2);
                            itemView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private String incomingIntent(){
        String folderName = "";
        if(getIntent().hasExtra("Name")){
            folderName = getIntent().getStringExtra("Name");
        }
        return folderName;
    }
}
