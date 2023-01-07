package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText id, password;
    Button login, signup ,nfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login_btn);
        signup = (Button) findViewById(R.id.sign_btn);
        nfc  =(Button) findViewById(R.id.nfc_btn);

        id = (EditText) findViewById(R.id.user_id);
        password = findViewById(R.id.pwd_txt);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);  // 회원가입 정보 출력
                startActivity(intent);
            }
        });
        nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, nfc.class);  // 회원가입 정보 출력
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = id.getText().toString();
                String user_pw = password.getText().toString();
                if(user_id.getBytes().length <= 0) {//빈값이 넘어올때의 처리
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else if(user_pw.getBytes().length <=0)
                {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Login.this, information.class);
                    startActivity(intent);
                }
            }
                    /*
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인에 성공한 경우
                                String user_id = jsonObject.getString("user_id");
                                String user_pw = jsonObject.getString("user_pw");
                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Picture.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("user_pw", user_pw);
                                startActivity(intent);
                            } else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(user_id, user_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });

    }

                     */

        });
    }
}



