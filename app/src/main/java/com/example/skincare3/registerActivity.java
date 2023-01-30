package com.example.skincare3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity {

    EditText username,password,enterpassword;
    Button buttonsignup, buttonsignin;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actvity);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.editTextTextPassword);
        enterpassword = (EditText)findViewById(R.id.repassword);

        buttonsignup = (Button) findViewById(R.id.buttonsignup);
        buttonsignin = (Button)  findViewById(R.id.buttonsignin);

        myDB = new DBHelper(this);

        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = enterpassword.getText().toString();

                if(user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(registerActivity.this, "fill all the fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(repass)){
                        Boolean usercheckResult = myDB.checkusername(user);
                        if (usercheckResult == false){
                            Boolean regResult = myDB.insertData(user,pass);
                            if (regResult == true)
                            {
                                Toast.makeText(registerActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),HomepgeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(registerActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(registerActivity.this, "user already exists. \n Please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(registerActivity.this, "Password not matching.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),loginActivity.class);
                startActivity(intent);
            }
        });
    }
}