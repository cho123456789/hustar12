package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends AppCompatActivity {
    EditText et_name, et_id, et_pass;
    Button enroll,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        et_name = (EditText) findViewById(R.id.user_name);
        et_id = (EditText) findViewById(R.id.user_id);
        et_pass = (EditText) findViewById(R.id.user_pwd);
        back = findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });

        enroll = (Button) findViewById(R.id.enroll_btn);
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.

                String user_id = et_id.getText().toString();
                String user_pw = et_pass.getText().toString();
                String user_nm = et_name.getText().toString();

                Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_SHORT).show();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            System.out.println("results"+success);
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Signup.this, Login.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                int user_sn =0;
                RegisterRequest registerRequest = new RegisterRequest(user_id, user_pw, user_nm, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Signup.this);
                queue.add(registerRequest);
            }
        });

    }
}