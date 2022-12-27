package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class information extends AppCompatActivity {

    Button btn_result, back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        back_btn = (Button) findViewById(R.id.btn_back); // 뒤로가기 -> 로그인
        btn_result = (Button) findViewById(R.id.button_result); // 입력결과확인

        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(information.this, Picture.class);
                startActivity(intent);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(information.this, Login.class);
                startActivity(intent); // 이벤트 시작하는 코드
                finish(); // 이벤트 종료
            }
        });
    }
}



//https://g-y-e-o-m.tistory.com/48