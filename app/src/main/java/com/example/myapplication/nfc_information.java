package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class nfc_information extends AppCompatActivity {
    TextView txt_name, txt_year, txt_age, txt_p_name, txt_p_number1, txt_p_number2;
    ImageView img1, img2;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_information);
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        //TextView text_tv = findViewById(R.id.txt1);
        txt_name = findViewById(R.id.txt_name);
        txt_year = findViewById(R.id.txt_year);
        txt_age = findViewById(R.id.txt_age);
        txt_p_name = findViewById(R.id.txt_p_name);
        txt_p_number1 = findViewById(R.id.txt_p_number);
        txt_p_number2 = findViewById(R.id.txt_p_number2);
        img1 = findViewById(R.id.dect1);
        //text_tv.setText(text);
        ProgressDialog progressDialog , progressDialog1;

        if (text.equals("7d1fe0ed"))
        {
            storageReference = FirebaseStorage.getInstance().getReference("my_folder/" + "AA134HmM" + ".png");
            // 다운로드할 파일명 설정
            try {
                progressDialog = new ProgressDialog(nfc_information.this);
                progressDialog.setMessage("Fetching image...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                File localfile = File.createTempFile("tempfile", ".jpg");
                // 임시폴더 경로 가주고오기
                storageReference.getFile(localfile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                                // 대화상자 종료

                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                txt_name.setText("아이이름 : 카리나");
                                txt_year.setText("생년월일 : 2000-04-11");
                                txt_age.setText("성별 : 여");
                                txt_p_name.setText("보호자 이름: 에스파");
                                txt_p_number1.setText("부) 연락처  : 010-1252-4475");
                                txt_p_number1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01077777777"));
                                        startActivity(tt);
                                    }
                                });
                                txt_p_number2.setText("모 ) 연락처 : 010-2131-4142");
                                //  가주온 파일을 비트맵으로 디코딩실시
                                int mDegree = 0;
                                mDegree = mDegree + 360;
                                img1 = findViewById(R.id.dect1);
                                img1.setImageBitmap(rotateImage(bitmap, mDegree));
                                //binding.dect1.setImageBitmap(bitmap);
                                // 이미지뷰에 이미지 표시하기
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference = database.getReference("/test/nfc");
                                databaseReference.setValue("0");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "Failed to retrieve", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (text.equals("265a4411"))
        {
            txt_name.setText("아이이름 : 윈터");
            txt_year.setText("생년월일 : 2001-01-01");
            txt_age.setText("성별 : 여");
            txt_p_name.setText("보호자 이름: 에스파");
            txt_p_number1.setText("부) 연락처  :010-3422-4400");
            txt_p_number2.setText("모 ) 연락처 :010-2211-4100");
            storageReference = FirebaseStorage.getInstance().getReference("my_folder/" + "winter" + ".png");
            // 다운로드할 파일명 설정
            try {
                progressDialog1 = new ProgressDialog(nfc_information.this);
                progressDialog1.setMessage("Fetching image...");
                progressDialog1.setCancelable(false);
                progressDialog1.show();
                File localfile = File.createTempFile("tempfile", ".jpg");
                // 임시폴더 경로 가주고오기
                storageReference.getFile(localfile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                if (progressDialog1.isShowing())
                                    progressDialog1.dismiss();
                                // 대화상자 종료

                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                //  가주온 파일을 비트맵으로 디코딩실시
                                int mDegree = 0;
                                mDegree = mDegree + 360;
                                img1 = findViewById(R.id.dect1);
                                img1.setImageBitmap(rotateImage(bitmap, mDegree));
                                //binding.dect1.setImageBitmap(bitmap);
                                // 이미지뷰에 이미지 표시하기
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference = database.getReference("/test/nfc");
                                databaseReference.setValue("0");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "Failed to retrieve", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }
    public Bitmap rotateImage(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }
}
