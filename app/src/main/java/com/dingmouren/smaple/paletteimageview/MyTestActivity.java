package com.dingmouren.smaple.paletteimageview;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyTestActivity extends AppCompatActivity {


    private ImageView img;
    private LinearLayout layout;
    private TextView title,body;

    private List<Palette.Swatch> swatches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_test);
        initView();
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

        if (bitmap == null) {
            return;
        }

        Palette.from(bitmap).generate(listener);

    }

    private Palette.PaletteAsyncListener listener = new Palette.PaletteAsyncListener() {
        @Override
        public void onGenerated(Palette palette) {
            if (palette != null) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                Palette.Swatch vibrantLight = palette.getLightVibrantSwatch();
                Palette.Swatch vibrantDark = palette.getDarkVibrantSwatch();
                Palette.Swatch mute = palette.getMutedSwatch();
                Palette.Swatch muteLight = palette.getLightMutedSwatch();
                Palette.Swatch muteDark = palette.getDarkMutedSwatch();

                swatches.add(vibrant);
                swatches.add(vibrantLight);
                swatches.add(vibrantDark);
                swatches.add(mute);
                swatches.add(muteLight);
                swatches.add(muteDark);

                title.setTextColor(swatches.get(3).getTitleTextColor());
                body.setTextColor(swatches.get(3).getBodyTextColor());
                layout.setBackgroundColor(swatches.get(3).getRgb());
            }
        }
    };

    private void initView() {
        img = (ImageView) findViewById(R.id.img);
        title = (TextView) findViewById(R.id.title);
        body = (TextView) findViewById(R.id.body);
        layout = (LinearLayout) findViewById(R.id.layout);
    }
}
