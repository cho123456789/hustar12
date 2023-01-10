package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ItemViewHolder> {

    ArrayList<Kid> kidList = new ArrayList<Kid>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.onBind(kidList.get(position));
    }

    @Override
    public int getItemCount() {
        return kidList.size();
    }

    public void addItem(Kid kid) {
        kidList.add(kid);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView textView5;
        private TextView textView6;
        private TextView textView7;

        private ImageView imageView1;

        private String kid_img_url;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.kid_nm);
            textView2 = itemView.findViewById(R.id.kid_gend);
            textView3 = itemView.findViewById(R.id.kid_bir);
            textView4 = itemView.findViewById(R.id.kid_guard_nm);
            textView5 = itemView.findViewById(R.id.kid_guard_tel1);
            textView6 = itemView.findViewById(R.id.kid_guard_tel2);
            //textView7 = itemView.findViewById(R.id.user_id);

            imageView1 = itemView.findViewById(R.id.camera);
        }

        // 실제 데이터를 1개의 객체마다 1:1 대응하여 바인딩시킨다.
        void onBind(Kid kid) {
            textView1.setText(kid.getKid_nm());
            //textView2.setText(kid.getUser_id());
            textView2.setText(kid.getKid_gend());
            textView3.setText(kid.getKid_bir());
//            textView3.setText(Integer.toString(kid.getKid_bir());
            textView4.setText(kid.getKid_guard_nm());
            textView5.setText(kid.getKid_guard_tel1());
            textView6.setText(kid.getKid_guard_tel2());

            String kid_img_url = kid.getKid_img_url();

            Glide.with(itemView)
                    .load(kid_img_url)
                    .placeholder(R.drawable.ic_circle_24)
                    .error(R.drawable.ic_email_24)
                    .centerCrop()
                    .placeholder(R.drawable.ic_florist_24)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView1);
        }
    }
}