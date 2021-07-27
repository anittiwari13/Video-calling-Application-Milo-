package com.project.milo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;

    ProgressDialog dialog;
    EditText emailbox, passwordbox;
    Button loginbutton, signupbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog= new ProgressDialog(this);
        dialog.setMessage("Please wait...");

        auth= FirebaseAuth.getInstance();
        emailbox= findViewById(R.id.emailbox);
        passwordbox= findViewById(R.id.passwordbox);
        loginbutton= findViewById(R.id.Loginbutton);
        signupbutton= findViewById(R.id.createbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email,password;
                email= emailbox.getText().toString();
                password= passwordbox.getText().toString();

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successful !",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,Dashboard_Activity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        //loginbutton
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUp_Activity.class));
            }
        });
    }
}