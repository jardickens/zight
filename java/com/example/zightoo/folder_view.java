package com.example.zightoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class folder_view extends AppCompatActivity implements dialogActivity.dialogListener {

    private RecyclerView.Adapter folderAdapter;
    private RecyclerView folderView;
    private Button newDevBtn;
    private TextView deviceText;
    private List<String> deviceID = new ArrayList<>();
    private List<folderitems>  folderList;
    private String devText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_view);

        folderView = (RecyclerView) findViewById(R.id.folderView);
        folderView.setHasFixedSize(true);
        folderView.setLayoutManager(new LinearLayoutManager(this));
        newDevBtn = (Button) findViewById(R.id.newDev);
        deviceText = (TextView) findViewById(R.id.devID);
        folderList = new ArrayList<>();


        folderList.add(
                new folderitems("Butterflies",R.drawable.butterfly));
        folderList.add(
                new folderitems("Chickens",R.drawable.chicken));
        folderList.add(
                new folderitems("Elephants",R.drawable.elephant));
        folderList.add(
                new folderitems("Horses",R.drawable.horse));
        folderList.add(
                new folderitems("Spiders",R.drawable.spider));
        folderList.add(
                new folderitems("Squirrels",R.drawable.squirrel));

        folderAdapter  = new folderAdapter(this,folderList);
        folderView.setAdapter(folderAdapter);

        Query dataRef2 = FirebaseDatabase.getInstance().getReference("Devices")
                .orderByChild(FirebaseAuth.getInstance().getUid()).equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        dataRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                devText = "";
                deviceID.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    deviceID.add(snapshot.getKey());
                }
                for(String j: deviceID){
                    devText += "\n"+j;
                }
                deviceText.setText("Devices: "+devText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    newDevBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openDialog();

        }
    });

    }

    public void openDialog(){
        dialogActivity dialog = new dialogActivity();
        dialog.show(getSupportFragmentManager(),"Add new device");
    }

    @Override
    public void applyTexts(String devID) {
        FirebaseDatabase.getInstance().getReference("Devices").child(devID)
                .child(FirebaseAuth.getInstance().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        deviceText.setText("");
    }
}
