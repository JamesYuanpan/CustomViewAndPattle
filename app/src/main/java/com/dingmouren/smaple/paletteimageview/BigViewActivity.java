package com.dingmouren.smaple.paletteimageview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.dingmouren.paletteimageview.BigView;

import java.io.IOException;

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
