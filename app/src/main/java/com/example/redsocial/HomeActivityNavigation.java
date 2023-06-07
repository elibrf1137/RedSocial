package com.example.redsocial;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivityNavigation extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    ProfileFragment profileFragment;
    HomeFragment homeFragment;
    PulicationFragment pulicationFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Bundle getDatos;
    String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_navigation);
        initComponente();
        correoUsuario = getDatos.getString("correoUsuario");
        Log.d("Correo desde HomeActivityNavigation",correoUsuario);
        setFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.navigation_profile:
                        setFragment(profileFragment);
                        return true;

                    case R.id.navigation_add_publication:
                        setFragment(pulicationFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void initComponente() {


        bottomNavigationView = findViewById(R.id.bottonNavigationView);
        frameLayout = findViewById(R.id.frametLayout);

        homeFragment = new HomeFragment();

        profileFragment = new ProfileFragment();

        pulicationFragment = new PulicationFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frametLayout, new ProfileFragment());
        fragmentTransaction.commit();

        getDatos = getIntent().getExtras();

    }

    private void setFragment(Fragment fragment){
        Bundle correoUserBundle = new Bundle();
        correoUserBundle.putString("bundleCorreoUser",correoUsuario);

        fragment.setArguments(correoUserBundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frametLayout,fragment);
        transaction.commit();
    }
}