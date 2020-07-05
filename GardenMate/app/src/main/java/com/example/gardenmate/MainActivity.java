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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button btnLogin;
    private TextView signUp;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.et_email_login);
        pass = findViewById(R.id.et_pass_login);
        btnLogin = findViewById(R.id.btn_login);
        signUp = findViewById(R.id.tv_signUp);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        //autoLogin
        if (mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        //login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
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

                mDialog.setMessage("Logging In...");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            mDialog.dismiss();
                            startActivity(new Intent (getApplicationContext(), HomeActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Unsuccessful Login",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                });
            }
        });

        //goto reg text
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegActivity.class));
            }
        });
    }
}