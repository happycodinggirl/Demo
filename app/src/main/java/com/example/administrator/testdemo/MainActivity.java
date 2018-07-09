package com.example.administrator.testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RoundChatView roundChatView=findViewById(R.id.roundChatView);
        roundChatView.setOrderMoney(145,80,25);
    }
}
