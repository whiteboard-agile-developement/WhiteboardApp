package com.jack.whiteboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome extends AppCompatActivity {

    CalendarView calendarView;

    Button logout;
    FirebaseAuth firebaseAuth;
    Button save;
    EditText name,age,email;
    String name1,age1,email1;
    TextView show,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        logout=(Button)findViewById(R.id.logout);
        firebaseAuth=FirebaseAuth.getInstance();
        save=(Button)findViewById(R.id.ebutton);
        name=(EditText) findViewById(R.id.ename);
        age=(EditText) findViewById(R.id.eage);
        email=(EditText) findViewById(R.id.email);
        show=(TextView)findViewById(R.id.show);
        home=(TextView)findViewById(R.id.home);


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),User_Profile.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1=name.getText().toString();
                age1=age.getText().toString();
                email1=email.getText().toString();
                if(name1.equals("")||age1.equals("")||email1.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fill All The Fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendUserValues();
                    Toast.makeText(getApplicationContext(), "Values are Added Successfully", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    age.setText("");
                    email.setText("");
                }

            }
        });


        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                finish();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    private void sendUserValues()
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference(firebaseAuth.getUid());
        UserValues userValues=new UserValues(name1,age1,email1);
        myRef.setValue(userValues);


    }


}
