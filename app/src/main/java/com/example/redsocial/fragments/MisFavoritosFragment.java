package com.example.redsocial.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.redsocial.HomeActivityNavigation;
import com.example.redsocial.R;
import com.example.redsocial.publicaciones.AdaptadorFavoritos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class MisFavoritosFragment extends Fragment {
    private String correoUser;
    private FirebaseFirestore databaseReference;
    private HashMap<String, String> listaPublicaciones;
    private RecyclerView recyclerViewPublicaciones;
    private AdaptadorFavoritos adaptadorFavoritos;
    private View miView;

    public MisFavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        miView = inflater.inflate(R.layout.fragment_mis_favoritos, container, false);
        recyclerViewPublicaciones = miView.findViewById(R.id.publicacionesFavoritasRV);
        initComponents();
        consultaDatos();
        return miView;
    }

    private void initComponents() {
        listaPublicaciones = new HashMap<>();
        correoUser = HomeActivityNavigation.getCorreoUsuario();
        databaseReference = FirebaseFirestore.getInstance();
    }

    private void consultaDatos() {
        DocumentReference documentoRef = databaseReference.collection("Users").document(correoUser);
        // Obtén el documento
        documentoRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists() && document.get("Favoritos") != null) {
                        // Obtiene el valor del campo de tipo array
                        ArrayList<String> publicaciones = (ArrayList<String>) document.get("Favoritos");
                        if (publicaciones.size() > 0) {
                            for (int i = 0; i < publicaciones.size(); i++) {
                                listaPublicaciones.put(correoUser+(i), publicaciones.get(i));
                            }
                            mostrarDatos();
                        }
                    } else {
                        Log.d("TAG", "El documento no existe");
                    }
                } else {
                    Log.d("TAG", "Error obteniendo el documento: ", task.getException());
                }
            }
        });
    }

    private void mostrarDatos() {
        recyclerViewPublicaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorFavoritos = new AdaptadorFavoritos(miView.getContext(), listaPublicaciones);
        recyclerViewPublicaciones.setAdapter(adaptadorFavoritos);
    }
}