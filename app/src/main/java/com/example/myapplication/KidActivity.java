package com.example.myapplication;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KidActivity extends AppCompatActivity {

    private EditText et_nm, et_gend, et_bir, et_gn, et_gt1, et_gt2;
    private String gender;
    private RadioGroup rg_gend;
    private Button btn_register;
    RequestQueue requestQueue;
    String insertUrl = "http://10.1.4.110/select7.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_kidregister);

        Intent intent = getIntent();

        // 아이디 값 찾아주기
//        et_id = findViewById(R.id.user_id);
        et_nm = findViewById(R.id.kid_nm);
//        et_gend = findViewById(R.id.kid_gend);
        et_bir = findViewById(R.id.kid_bir);
        et_gn = findViewById(R.id.kid_guard_nm);
        et_gt1 = findViewById(R.id.kid_guard_tel1);
        et_gt2 = findViewById(R.id.kid_guard_tel2);

        rg_gend = findViewById(R.id.kid_gender);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.regi_btn);
        btn_register.setOnClickListener(new View.OnClickListener() {

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    /*
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) { // 회원등록에 성공한 경우
                            Toast.makeText(getApplicationContext(), "정보 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(KidActivity.this, Picture.class);
                            startActivity(intent);
                        } else { // 회원등록에 실패한 경우
                            Toast.makeText(getApplicationContext(), "정보 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                     */
                }

            };

            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {




                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "정보 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KidActivity.this, Picture.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(), "정보 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters  = new HashMap<String, String>();
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> parameters = new HashMap<String, String>();
//                            parameters.put("firstname",firstname.getText().toString());
//                            parameters.put("lastname",lastname.getText().toString());
//                            parameters.put("age",age.getText().toString());



                        parameters.put("user_id", intent.getStringExtra("user_id"));
                        parameters.put("kid_nm", et_nm.getText().toString());
//                        parameters.put("kid_gend", et_gend.getText().toString());
                        parameters.put("kid_gend", gender);
                        parameters.put("kid_bir", et_bir.getText().toString());
                        parameters.put("kid_guard_nm", et_gn.getText().toString());
                        parameters.put("kid_guard_tel1", et_gt1.getText().toString());
                        parameters.put("kid_guard_tel2", et_gt2.getText().toString());
                        return parameters;
                    }
                };
                requestQueue.add(request);

                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
            }
                // 서버로 Volley를 이용해서 요청을 함.
//                KidRequest kidRequest = new KidRequest(user_id,kid_nm,kid_gend,kid_bir,kid_guard_nm,kid_guard_tel1,kid_guard_tel2,responseListener);
//                RequestQueue queue = Volley.newRequestQueue(KidActivity.this);
//                queue.add(kidRequest);

        });

        rg_gend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    gender = "M";
                } else if (checkedId == R.id.female) {
                    gender = "F";
                }
            }
        });
    }
}



