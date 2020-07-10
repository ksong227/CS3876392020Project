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


public class RegFragment extends Fragment {

    private EditText email;
    private EditText pass;
    private Button btnReg;
    private TextView login;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;


    public RegFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_reg, container, false);

        email = view.findViewById(R.id.et_email_reg);
        pass = view.findViewById(R.id.et_pass_reg);
        btnReg = view.findViewById(R.id.btn_reg);
        login = view.findViewById(R.id.tv_login);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(getActivity());

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
                if (mPass.length()<6)
                {
                    pass.setError("Must be at least 6 characters");
                    return;
                }

                mDialog.setMessage("Processing...");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getActivity(),"Registration Complete!",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            startActivity(new Intent(getActivity(), ImagesActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Unsuccessful Registration",Toast.LENGTH_SHORT).show();
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
                Fragment frag = new LoginFragment();
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