package com.example.zightoo;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.StorageReference;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signupEmail,signupPassword, deviceID;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupEmail = (EditText) findViewById(R.id.signupEmail);
        signupPassword = (EditText) findViewById(R.id.signupPassword);
        deviceID = (EditText) findViewById(R.id.deviceID);
        findViewById(R.id.signupBtn).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();


    }

    private void registerUser(){
        final String email = signupEmail.getText().toString().trim();
        String password  = signupPassword.getText().toString().trim();
        final String devID = deviceID.getText().toString().trim();

        if (email.isEmpty()){
            signupEmail.setError("Email is required");
            signupEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signupEmail.setError("Input a valid email");
            signupEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            signupPassword.setError("Password is required");
            signupPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            signupPassword.setError("Minimum length of password should be 6");
            signupPassword.requestFocus();
            return;
        }
        if (devID.isEmpty()){
            signupEmail.setError("Email is required");
            signupEmail.requestFocus();
            return;
        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            signupEmail.setError("Input a valid email");
//            signupEmail.requestFocus();
//            return;
//        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, folder_view.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Devices");
                    ref.child(devID).child(FirebaseAuth.getInstance().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    startActivity(intent);

                }else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                        Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signupBtn:
                registerUser();
                break;

            case R.id.loginText:
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }
    }
}
