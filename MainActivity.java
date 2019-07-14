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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText username,password;
    private Button signin;
    private TextView signup,forget;
    FirebaseAuth firebaseAuth;
    String user,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username= (EditText)findViewById(R.id.username);
        password= (EditText)findViewById(R.id.password);
        signin= (Button)findViewById(R.id.sign_in);
        signup= (TextView)findViewById(R.id.sign_up);
        forget= (TextView)findViewById(R.id.forget);


        firebaseAuth=FirebaseAuth.getInstance();

        //Email Verification using Firebase

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), Sign_up.class);
                startActivity(i);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Forgot_Password.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=username.getText().toString();
                pass=password.getText().toString();
                if(valid())
                {
                    firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                /*finish();
                                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),Welcome.class);
                                startActivity(i);*/
                                checkEmail();
                            }
                            else{
                                firebaseAuth.signOut();
                                Toast.makeText(getApplicationContext(),"Invalid User",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });

    }

    private Boolean valid()
    {
        Boolean result=false;

        if(user.equals("")||pass.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Fill All The Fields", Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
        }

        return result;
    }

    private void checkEmail()
    {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        Boolean emailverify=firebaseUser.isEmailVerified();  // returns true or false

        if(emailverify)
        {
            finish();
            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Welcome.class));
        }
        else {
            firebaseAuth.signOut();
            Toast.makeText(getApplicationContext(),"Verify your Email",Toast.LENGTH_SHORT).show();
        }

    }


}
