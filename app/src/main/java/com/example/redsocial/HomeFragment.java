package com.example.redsocial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment {
    String correoUser;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(getArguments() != null){
            correoUser = getArguments().getString("bundleCorreoUser");
        }
        Toast.makeText(getContext(),"Correo de usuario: "+ correoUser,Toast.LENGTH_SHORT);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}