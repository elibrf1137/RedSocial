package com.example.redsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivityNavigation extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    ProfileFragment profileFragment;
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_navigation);
        initComponente();
        setFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        setFragment(profileFragment);
                        return true;
                    case R.id.navigation_profile:
                        setFragment(homeFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void initComponente() {
        bottomNavigationView = findViewById(R.id.bottonNavigationView);
        frameLayout = findViewById(R.id.frametLayout);

        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment();

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frametLayout,fragment);
        transaction.commit();
    }
}