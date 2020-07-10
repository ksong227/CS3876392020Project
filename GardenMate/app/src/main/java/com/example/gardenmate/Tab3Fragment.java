package com.example.gardenmate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tab3Fragment extends Fragment {

    private EditText editText;
    private ExtendedFloatingActionButton fab_save;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseRef = database.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid;

    public static Upload item;

    public Tab3Fragment() {
        // Required empty public constructor
    }

    public static Tab3Fragment newInstance(Upload item) {
        Tab3Fragment fragment = new Tab3Fragment();
        Bundle args = new Bundle();
        args.putSerializable("Upload", item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = (Upload) getArguments().getSerializable("Upload");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_tab3, container, false);

        //getUId and key
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                uid = profile.getUid().replace("@","").replace(".","");
            }
        }
        final String key = item.getKey();

        editText = view.findViewById(R.id.edit_text_notes);
        fab_save = view.findViewById(R.id.fab_save);

        editText.setText(item.getNotes());

        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef.child(uid).child(key).child("notes").setValue(editText.getText().toString());
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}