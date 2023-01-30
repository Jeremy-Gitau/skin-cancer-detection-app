package com.example.skincare3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    EditText username,password;
    Button buttonlogin;
    DBHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernamelog);
        password = (EditText) findViewById(R.id.password2);
        buttonlogin = (Button) findViewById(R.id.buttonlog);

        myDB = new DBHelper(this);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user.equals("") || pass.equals("")){
                    Toast.makeText(loginActivity.this, "please enter the credentials.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean result = myDB.checkusernamePassword(user,pass);

                    if(result == true){

                        Intent intent = new Intent(getApplicationContext(),HomepgeActivity.class);
                    }
                    else{
                        Toast.makeText(loginActivity.this,"Invalid credentials.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}