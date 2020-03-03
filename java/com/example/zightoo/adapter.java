package com.example.zightoo;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {
    private String emails = "";
    private Context context;
    private List<listitem> listitems;



    public adapter(Context context, List<listitem> listitems) {
        this.context = context;
        this.listitems = listitems;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView confidence;
        public TextView timestamp;
        public TextView is_public;
        public TextView user_Email;
        public ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            confidence = (TextView) itemView.findViewById(R.id.confidence);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            user_Email = (TextView) itemView.findViewById(R.id.user_ID);
            is_public = (TextView) itemView.findViewById(R.id.is_public);


        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder ViewHolder, int i) {

        final listitem listItem = listitems.get(i);
        String doubleValue = Double.toString(listItem.getConfidence());
        ViewHolder.confidence.setText("Confidence: "+doubleValue);
        ViewHolder.timestamp.setText("Date&Time: "+listItem.getdate());
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Devices").child(listItem.getDevice_ID());
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    emails+= "\n"+snapshot.getValue().toString();
                    ViewHolder.user_Email.setText("User Email: "+ emails);

                }
                emails = "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(listItem.getIs_public().toLowerCase().equals("true")){
            ViewHolder.is_public.setText("Privacy: Public");

        }else if(listItem.getIs_public().toLowerCase().equals("false")){
            ViewHolder.is_public.setText("Privacy: Private");
        }

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child(listItem.getimage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                Glide.with(context).load(imageUrl).into(ViewHolder.imageView);
//                Log.d("Url",  imageUrl);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }
}

