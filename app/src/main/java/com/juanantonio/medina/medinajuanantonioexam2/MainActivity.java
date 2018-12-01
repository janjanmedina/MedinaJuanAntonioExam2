package com.juanantonio.medina.medinajuanantonioexam2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText fname;
    EditText lname;
    EditText examone;
    EditText examtwo;
    TextView average;

    FirebaseDatabase db;
    DatabaseReference myRef;

    ArrayList<String> keyList;

    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("grade");

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        examone = (EditText) findViewById(R.id.examone);
        examtwo = (EditText) findViewById(R.id.examtwo);
        average = (TextView) findViewById(R.id.average);

        keyList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                keyList.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    student = ds.getValue(Student.class);
                    keyList.add(ds.getKey());
                }

                average.setText("Your average is: " + student.getAverage());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("FIREBASE", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

    public void addAverage(View v) {
        String userFname = fname.getText().toString();
        String userLname = lname.getText().toString();
        int userExamOne = Integer.parseInt(examone.getText().toString());
        int userExamTwo = Integer.parseInt(examtwo.getText().toString());

        Student student = new Student(userFname, userLname, computeAverage(userExamOne, userExamTwo));

        String key = myRef.push().getKey();
        myRef.child(key).setValue(student);
    }

    public long computeAverage(int examOne, int examTwo) {
        return (long) (examOne + examTwo) / 2;
    }
}
