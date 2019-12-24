package com.dingmouren.smaple.paletteimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dingmouren.paletteimageview.BigView;

import java.io.IOException;
import java.io.InputStream;

public class BigViewActivity extends AppCompatActivity {


    private BigView bigView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_view);
        bigView = (BigView) findViewById(R.id.bigview);
        try {
            bigView.setImage(getResources().getAssets().open("img2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
