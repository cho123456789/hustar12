package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserResultActivitiy extends AppCompatActivity {
    Button Kid_btn;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_result_activitiy);
        Kid_btn = findViewById(R.id.addcategoryBtn);
        Intent intent = getIntent();
        String n = intent.getStringExtra("user_id");
        Kid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserResultActivitiy.this, KidActivity.class);
                intent.putExtra("user_id",n);
                startActivity(intent);
            }
        });
    }
}