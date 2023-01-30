package com.example.skincare3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intents = new Intent(MainActivity.this, registerActivity.class);
                startActivity(intents);
                finish();

            }
        },5000);




//        Button button = (Button) findViewById(R.id.BUTTON);
//
//
//        button.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openActivity2();
//            }
//        });

    }



//    public void openActivity2(){
//        Intent intent = new Intent(this, HomepgeActivity.class);
//
//        startActivity(intent);
//    }


}