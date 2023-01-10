package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BookList01Activity extends AppCompatActivity {
    private BookRecyclerAdapter adapter;
    Button start, stop ,btnadd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list01);
        setTitle("우리아이");
        start = findViewById(R.id.start_nfc_scan);
        stop  = findViewById(R.id.stop_nfc_stop);
        btnadd = findViewById(R.id.addcategoryBtn);
        RecyclerView recyclerView1 = findViewById(R.id.recyclerView1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setHasFixedSize(true);
        adapter = new BookRecyclerAdapter();
        recyclerView1.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("/test/nfc");
                myRef.setValue("1");
                start.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("/test/nfc");
                myRef.setValue("0");
                stop.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookList01Activity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        ThreadProc();

    }

    // 쓰레드 처리
    private void ThreadProc() {

        new Thread() {
            @Override
            public void run() {
                //super.run();
                String str,receiveMsg = "";

                String urlStr = "http://10.1.4.110/json3.php";
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                    //conn.setRequestProperty("x-waple-authorization", clientKey);
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
                        //Log.i("receiveMsg : ", receiveMsg);
                        reader.close();

                        Bundle bun = new Bundle();
                        bun.putString("jsonGap",receiveMsg);
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);

                    } else {
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void jsonParsing(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray kidArr = jsonObject.getJSONArray("kid");

            for (int i=0;i<kidArr.length();i++) {
                JSONObject kidObj = kidArr.getJSONObject(i);

                Kid kid = new Kid();
//                kid.setGoods_no(Integer.parseInt(kidObj.getString("goods_no")));
//                kid.setGoods_name(kidObj.getString("goods_name"));
//                kid.setSell_price(Integer.parseInt(kidObj.getString("sell_price")));
//                kid.setGoods_writer(kidObj.getString("goods_writer"));
//                kid.setGoods_detail1(kidObj.getString("goods_detail1"));
//                kid.setGoods_pic1(kidObj.getString("goods_pic1"));

//                kid.setKid_sn(Integer.parseInt(kidObj.getString("kid_sn")));
                kid.setKid_nm(kidObj.getString("kid_nm"));
                kid.setKid_gend(kidObj.getString("kid_gend"));
                kid.setKid_bir(kidObj.getString("kid_bir"));
                kid.setKid_guard_nm(kidObj.getString("kid_guard_nm"));
                kid.setKid_guard_tel1(kidObj.getString("kid_guard_tel1"));
                kid.setKid_guard_tel2(kidObj.getString("kid_guard_tel2"));
                kid.setUser_id(kidObj.getString("user_id"));
                kid.setKid_img_url(kidObj.getString("kid_img_url"));


//                kid.setGoods_pic1(kidObj.getString("goods_pic1"));

                // BookRecyclerAdapter에 Book 
                adapter.addItem(kid);
            }

            adapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON Parsing Error",e.getMessage());
        }
    }

    /*
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            Bundle bun = msg.getData();
            String str = bun.getString("jsonGap");
 
            jsonParsing(str);
        }
    };
     */
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bun = msg.getData();
            String str = bun.getString("jsonGap");

            jsonParsing(str);
            return true;
        }
    });
}
