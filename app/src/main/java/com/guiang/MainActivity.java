package com.guiang.aaron;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText etFName, etAge, etGender;
    Button btnSearch, btnSave;
    TextView viewName, viewAge, viewGender;

    FirebaseDatabase database = FirebaseDatabase.getInstance();;
    DatabaseReference myRef = database.getReference("Record");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFName = findViewById(R.id.inName);
        etAge = findViewById(R.id.inAge);
        etGender = findViewById(R.id.inGender);
        btnSave = findViewById(R.id.SaveButton);
        btnSearch = findViewById(R.id.SearchButton);
        viewName = findViewById(R.id.resName);
        viewAge = findViewById(R.id.resAge);
        viewGender = findViewById(R.id.resGender);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = etFName.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String gender = etGender.getText().toString().trim();
                if(fullname.isEmpty() || age.isEmpty() || gender.isEmpty()){
                    if(fullname.isEmpty()){
                        etFName.setError("Please input field.");
                    }
                    if(age.isEmpty()){
                        etAge.setError("Please input field.");
                    }
                    if(gender.isEmpty()){
                        etGender.setError("Please input field.");
                    }
                }else{

                    Record rec = new Record(age,gender);
                    myRef.child(fullname).setValue(rec);
                    Toast.makeText(MainActivity.this, "Record Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etFName.getText().toString().trim();

                if(name.isEmpty()){
                    etFName.setError("Please input field");
                }else{
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int status = 0;
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                if(ds.getKey().equals(name)){
                                    Toast.makeText(MainActivity.this, "Name found.", Toast.LENGTH_SHORT).show();
                                    String age = (String) ds.child("age").getValue();
                                    String gender = (String) ds.child("gender").getValue();
                                    viewName.setText(name);
                                    viewAge.setText(age);
                                    viewGender.setText(gender);
                                    status = 1;
                                }
                            }
                            if(status != 1){
                                Toast.makeText(MainActivity.this, "Name not found.", Toast.LENGTH_SHORT).show();
                                viewName.setText("");
                                viewAge.setText("");
                                viewGender.setText("");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


    }
}
