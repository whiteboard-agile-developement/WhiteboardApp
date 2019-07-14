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

import org.w3c.dom.Text;

public class Sign_up extends AppCompatActivity {

    private Button signup;
    private TextView signin;
    private EditText name,username,password;
    FirebaseAuth firebaseAuth;
    String name1,username1,password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        signup= findViewById(R.id.sign_up);
        signin= findViewById(R.id.sign_in);
        name= findViewById(R.id.name);
        username= findViewById(R.id.username);
        password= findViewById(R.id.password);

        firebaseAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1 = name.getText().toString();
                username1 = username.getText().toString();
                password1 = password.getText().toString();

            if (valid())
            {
                firebaseAuth.createUserWithEmailAndPassword(username1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {

                            /*Toast.makeText(getApplicationContext(), "Signed Up Successful", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);*/
                            //calling send email method
                            sendEmail();

                        } else {
                            Toast.makeText(getApplicationContext(), "Error Sign Up", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        });



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
        }
    });
    }

    private Boolean valid()
    {
        Boolean result=false;

        if(name1.equals("")||username1.equals("")||password1.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Fill All the Fields",Toast.LENGTH_SHORT).show();
        }
        else
        {
            result=true;
        }

        return result;
    }

    private void sendEmail()
    {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        finish();
                        firebaseAuth.signOut();
                        Toast.makeText(getApplicationContext(),"Sign Up Successfull. verification send to your email!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Sign Up failed. verification not send to your email!",Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }
    }

}
