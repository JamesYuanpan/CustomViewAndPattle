package com.dingmouren.smaple.paletteimageview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.dingmouren.paletteimageview.CustomView;

public class CustomViewActivity extends AppCompatActivity {

    private CustomView customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        customView = (CustomView) findViewById(R.id.progress);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    final int progress = i;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customView.setProgress(progress);
                        }
                    });
                }
            }
        }).start();
    }
}
