package com.dingmouren.smaple.viewpager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingmouren.smaple.paletteimageview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPager2Activity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new Viewpager2Adapter());
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    }
}

class Viewpager2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<String> datas = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
            return new ViewpagerHolder(view);
            }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#f2da45"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#345df4"));
        }
        ((ViewpagerHolder) holder).textView.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewpagerHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewpagerHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}