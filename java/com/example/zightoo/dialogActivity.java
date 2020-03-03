package com.example.zightoo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class dialogActivity extends AppCompatDialogFragment {
    private EditText devIDedit;
    private dialogListener listener;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        devIDedit = view.findViewById(R.id.edit_devID);
        builder.setView(view)
                .setTitle("Add new device")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String devID = devIDedit.getText().toString().trim();
                        if(devID.isEmpty()) {
                            devIDedit.setError("Device ID is required");
                            devIDedit.requestFocus();
                            Toast toast = Toast.makeText(getContext(),"Device ID cannot be empty, please input again",Toast.LENGTH_LONG);
                            toast.show();
                        }else {
                            listener.applyTexts(devID);
                        }
                    }
                });
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (dialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+
                    "must implement dialogListern");
        }
    }
    public interface dialogListener{
        void applyTexts(String devID);
    }
}
