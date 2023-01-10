package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private String TAG = KidActivity.class.getSimpleName();
    private EditText et_id, et_pass;
    private Button btn_login, btn_register,nfc;
    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        webView = (WebView) findViewById(R.id.webview);

        et_id = findViewById(R.id.user_id);
        et_pass = findViewById(R.id.pwd_txt);
        btn_login = findViewById(R.id.login_btn);
        btn_register = findViewById(R.id.sign_btn);
        nfc  = findViewById(R.id.nfc_btn);

        WebSettings wsetting = webView.getSettings();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {// https 이미지.
            wsetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // webView.setWebViewClient(new WebViewClient());  // 새 창 띄우기 않기
        // webView.setWebChromeClient(new WebChromeClient());

      //  webView.getSettings().setLoadWithOverviewMode(true);  // WebView 화면크기에 맞추도록 설정 - setUseWideViewPort 와 같이 써야함
       // webView.getSettings().setUseWideViewPort(true);  // wide viewport 설정 - setLoadWithOverviewMode 와 같이 써야함

        // webView.getSettings().setSupportZoom(false);  // 줌 설정 여부
        // webView.getSettings().setBuiltInZoomControls(false);  // 줌 확대/축소 버튼 여부

       webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용여부
//        webview.addJavascriptInterface(new AndroidBridge(), "android");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // javascript가 window.open()을 사용할 수 있도록 설정

        // webView.getSettings().setDomStorageEnabled(true);  // 로컬 스토리지
        webView.loadUrl("https://www.safe182.go.kr/api/lcm/amberListTForm.do?esntlId=10000510&authKey=e5ab0c5fc44f4439&viewType=01");



        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, nfc.class);  // 회원가입 정보 출력
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String user_id = et_id.getText().toString();
                String user_pw = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인에 성공한 경우
                                String user_id = jsonObject.getString("user_id"); //id값을 DB에서 가져온다.

                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();//메세지보여준다
                                Intent intent = new Intent(LoginActivity.this, KidActivity.class);//로그인에서 아이등록으로 이동
                                intent.putExtra("user_id", user_id);//DB에서 가져온 user_id 값도 같이 넘겨준다.
                                startActivity(intent);//값넘기기!
                            } else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();//메세지 보여준다.
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(user_id, user_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });


    }
}