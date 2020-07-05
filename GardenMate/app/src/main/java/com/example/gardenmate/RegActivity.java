package com.example.gardenmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button btnReg;
    private TextView login;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        email = findViewById(R.id.et_email_reg);
        pass = findViewById(R.id.et_pass_reg);
        btnReg = findViewById(R.id.btn_reg);
        login = findViewById(R.id.tv_login);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        //reg button
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString().trim();
                String mPass = pass.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail))
                {
                    email.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(mPass))
                {
                    pass.setError("Password Required");
                    return;
                }

                mDialog.setMessage("Processing...");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Registration Complete!",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            startActivity(new Intent (getApplicationContext(), HomeActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Unsuccessful Registration",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                });
            }
        });

        //goto login text
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}