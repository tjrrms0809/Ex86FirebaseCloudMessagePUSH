package com.ahnsafety.ex86firebasecloudmessagepush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        tv=findViewById(R.id.tv);

        Intent intent= getIntent();
        String name= intent.getStringExtra("name");
        String msg= intent.getStringExtra("msg");

        tv.setText(name+"\n"+msg);
    }
}
