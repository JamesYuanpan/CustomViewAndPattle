package com.dingmouren.smaple.DrawerLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dingmouren.smaple.paletteimageview.R;
import com.google.android.material.navigation.NavigationView;

public class DrawerLayoutActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        button = findViewById(R.id.button1);
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(abdt);
        abdt.syncState();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
//        drawerLayout.closeDrawers();
    }
}
