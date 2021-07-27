package com.project.milo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp_Activity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText emailbox, passwordbox, namebox;
    Button loginbutton, signupbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);

        database= FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        emailbox= findViewById(R.id.emailbox);
        passwordbox= findViewById(R.id.passwordbox);
        loginbutton= findViewById(R.id.Loginbutton);
        signupbutton= findViewById(R.id.createbutton);
        namebox= findViewById(R.id.namebox);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password,name;
                email= emailbox.getText().toString();
                password= passwordbox.getText().toString();
                name= namebox.getText().toString();

                user user= new user();
                user.setEmail(email);
                user.setName(name);
                user.setPassword(password);
                ;

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //success
                            database.collection("users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SignUp_Activity.this, LoginActivity.class));
                                }
                            });
                            Toast.makeText(SignUp_Activity.this, "Account is Created",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUp_Activity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp_Activity.this, LoginActivity.class));
                //Toast.makeText(SignUp_Activity.this, "Reached Login Page",Toast.LENGTH_SHORT).show();
            }
        });
    }
}