package com.dingmouren.smaple.paletteimageview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.dingmouren.smaple.DrawerLayout.DrawerLayoutActivity;
import com.dingmouren.smaple.viewpager2.ViewPager2Activity;

public class OtherActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        findViewById(R.id.viewpager2).setOnClickListener(this);
        findViewById(R.id.drawer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewpager2:
                startActivity(new Intent(OtherActivity.this, ViewPager2Activity.class));
                break;
            case R.id.drawer:
                startActivity(new Intent(OtherActivity.this, DrawerLayoutActivity.class));
                break;
        }
    }
}
