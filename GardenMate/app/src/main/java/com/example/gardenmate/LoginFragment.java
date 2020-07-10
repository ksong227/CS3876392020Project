package com.example.gardenmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private EditText email;
    private EditText pass;
    private Button btnLogin;
    private TextView signUp;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.et_email_login);
        pass = view.findViewById(R.id.et_pass_login);
        btnLogin = view.findViewById(R.id.btn_login);
        signUp = view.findViewById(R.id.tv_signUp);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(getActivity());

        //autoLogin
        if (mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getActivity(), ImagesActivity.class));
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
                            startActivity(new Intent (getActivity(), ImagesActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Unsuccessful Login",Toast.LENGTH_SHORT).show();
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
                Fragment frag = new RegFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment1, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}