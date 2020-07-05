package com.example.gardenlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gardenlist.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //toolbar
        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List");

        //db auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("List").child(uId);
        mDatabase.keepSynced(true);

        //recyclerView
        recyclerView = findViewById(R.id.recycler_home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //fab
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog();
            }
        });
    }

    //add item popup
    private void addDialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
        View view = inflater.inflate(R.layout.input_data, null);
        final AlertDialog alertDialog = dialog.create();
        alertDialog.setView(view);

        final EditText name = view.findViewById(R.id.et_name);
        Button btnAdd = view.findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName = name.getText().toString().trim();

                if (TextUtils.isEmpty(mName))
                {
                    name.setError("Required");
                    return;
                }

                //add data to db
                String id = mDatabase.push().getKey();
                String date = DateFormat.getDateInstance().format((new Date()));

                Data data = new Data(id, date, mName);

                mDatabase.child(id).setValue(data);


                Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //recycler view
        FirebaseRecyclerAdapter<Data, itemViewHolder> adapter = new FirebaseRecyclerAdapter<Data, itemViewHolder>
                (Data.class, R.layout.item_data, itemViewHolder.class, mDatabase)
        {
            @Override
            protected void populateViewHolder(itemViewHolder itemViewHolder, Data data, int i) {
                itemViewHolder.setName(data.getName());
            }
        };

        recyclerView.setAdapter(adapter);
    }

    //view holder
    public static class itemViewHolder extends RecyclerView.ViewHolder
    {
        View view;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setName(String name)
        {
            TextView mName = view.findViewById(R.id.name);
            mName.setText(name);
        }
    }

}