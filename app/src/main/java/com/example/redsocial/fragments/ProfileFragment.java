package com.example.redsocial.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ProfileFragment extends Fragment {
    private String correoUser;
    private FirebaseFirestore databaseReference;
    private Button addPublicationButton;
    private HashMap<String, String> listaPublicaciones;

    private RecyclerView recyclerViewPublicaciones;

    private AdaptadorPublicaciones adaptadorPublicaciones;

    public String getCorreoUser() {
        return correoUser;
    }

    public HashMap<String,String> getListaPublicaciones() {
        return listaPublicaciones;
    }
    private View miView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null){
            correoUser = getArguments().getString("bundleCorreoUser");
        }
        miView = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerViewPublicaciones = miView.findViewById(R.id.listaPublicacionesRV);
        initComponents();
        consultaDatosDos();

        addPublicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                // Configura una transición de entrada y salida para los fragmentos
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                // Crea una instancia del fragmento secundario y lo muestra encima del fragmento actual
                transaction.add(android.R.id.content, new PulicationFragment()).addToBackStack(null).commit();

            }
        });
        return miView;
    }
    private void initComponents(){
        addPublicationButton = miView.findViewById(R.id.addPublicationButtonProfile);
        databaseReference = FirebaseFirestore.getInstance();
    }

    private void consultaDatos(){
        databaseReference.collection("Users").document("listaPublicaciones").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    String[] listaPublicacionesPersonales;
                    DocumentSnapshot ds= task.getResult();
                    Log.d("Lista de publicaciones personales",ds.getData().toString());
                }
            }
        });
    }
    private void consultaDatosDos(){
        DocumentReference docRef = databaseReference.collection("Users").document("listaPublicaciones");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Lista publicaciones personales", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("Lista publicaciones personales", "No such document");
                    }
                } else {
                    Log.d("Lista publicaciones personales", "get failed with ", task.getException());
                }
            }
        });
    }

    private void mostrarDatos(){
        recyclerViewPublicaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorPublicaciones = new AdaptadorPublicaciones(getContext(),listaPublicaciones);
        recyclerViewPublicaciones.setAdapter(adaptadorPublicaciones);
    }
}