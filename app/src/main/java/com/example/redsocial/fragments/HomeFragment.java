package com.example.redsocial.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.redsocial.R;
import com.example.redsocial.publicaciones.AdaptadorPublicaciones;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class HomeFragment extends Fragment {
    private String correoUser;
    private FirebaseFirestore databaseReference;
    private HashMap<String, String> listaPublicaciones;

    private RecyclerView recyclerViewPublicaciones;

    private AdaptadorPublicaciones adaptadorPublicaciones;

    public String getCorreoUser() {
        return correoUser;
    }

    public HashMap<String,String> getListaPublicaciones() {
        return listaPublicaciones;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerViewPublicaciones = view.findViewById(R.id.listaPublicacionesRV);
        initComponents();
        consultaDatos();
        return view;
    }

    private void consultaDatos(){
        databaseReference.collection("publicaciones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    String publicacion;
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        publicacion =  queryDocumentSnapshot.getData().toString().split("=")[1].substring(0,queryDocumentSnapshot.getData().toString().split("=")[1].length()-1);
                        listaPublicaciones.put(queryDocumentSnapshot.getId(),publicacion);
                    }
                    mostrarDatos();
                }
            }
        });
    }

    private void initComponents(){
        if(getArguments() != null){
            correoUser = getArguments().getString("bundleCorreoUser","Usuario");
        }
        databaseReference = FirebaseFirestore.getInstance();
        listaPublicaciones = new HashMap();
    }

    private void mostrarDatos(){
        recyclerViewPublicaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorPublicaciones = new AdaptadorPublicaciones(getContext(),listaPublicaciones);
        recyclerViewPublicaciones.setAdapter(adaptadorPublicaciones);
    }
}