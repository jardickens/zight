package com.example.zightoo;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;

public class folderAdapter extends RecyclerView.Adapter<folderAdapter.folderViewHolder>{

    private Context mCtx;
    private List<folderitems> folderItems;

    public folderAdapter(Context mCtx, List<folderitems> folderItems) {
        this.mCtx = mCtx;
        this.folderItems = folderItems;
    }


    class folderViewHolder extends RecyclerView.ViewHolder {

        ImageView imageFolder;
        TextView folderName;
        CardView parentCard;

        public folderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageFolder = itemView.findViewById(R.id.imageFolder);
            folderName = itemView.findViewById(R.id.folderName);
            parentCard = itemView.findViewById(R.id.parentCard);
        }
    }

    @NonNull
    @Override
    public folderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_folder, viewGroup, false);
        return new folderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final folderViewHolder folderViewHolder, int i) {

        final folderitems folderitem = folderItems.get(i);
        folderViewHolder.folderName.setText(folderitem.getLabel());
        Glide.with(mCtx).load(folderitem.getImage()).into(folderViewHolder.imageFolder);
        folderViewHolder.parentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx,ViewItemActivity.class);
                intent.putExtra("Name",folderitem.getLabel());
                mCtx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return folderItems.size();
    }

}
