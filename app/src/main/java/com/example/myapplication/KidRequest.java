package com.example.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class KidRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://10.1.4.110/select7.php";
    private Map<String, String> map;


    public KidRequest(String user_id, String kid_nm, String kid_gend, String kid_bir, String kid_guard_nm, String kid_guard_tel1, String kid_guard_tel2, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id",user_id);
        map.put("kid_nm", kid_nm);
        map.put("kid_gend",kid_gend);
        map.put("kid_bir", kid_bir);
        map.put("kid_guard_nm",kid_guard_nm);
        map.put("kid_guard_tel1", kid_guard_tel1);
        map.put("kid_guard_tel2", kid_guard_tel2);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
