package com.example.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.123.104/select5.php";
    private Map<String, String> map;


    public RegisterRequest(String user_id, String user_pw, String user_nm, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        //map.put("user_sn",user_sn+"");
        map.put("user_id",user_id);
        map.put("user_pw", user_pw);
        map.put("user_nm", user_nm);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
