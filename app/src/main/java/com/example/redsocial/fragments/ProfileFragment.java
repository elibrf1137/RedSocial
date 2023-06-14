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

import com.example.redsocial.HomeActivityNavigation;
import com.example.redsocial.R;
import com.example.redsocial.publicaciones.AdaptadorPublicaciones;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileFragment extends Fragment {
    private String correoUser;
    private FirebaseFirestore databaseReference;
    private Button addPublicationButton;
    private Button moveFavoritePublications;
    private HashMap<String, String> listaPublicaciones;
    private RecyclerView recyclerViewPublicaciones;
    private AdaptadorPublicaciones adaptadorPublicaciones;
    private View miView;
    MisFavoritos misFavoritosFragment;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        miView = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerViewPublicaciones = miView.findViewById(R.id.listaPublicacionesPersonales);
        initComponents();
        //Toast.makeText(getContext(),correoUser,Toast.LENGTH_SHORT).show();
        consultaDatos();

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

        moveFavoritePublications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.frametLayout,misFavoritosFragment);
                transaction.commit();
            }
        });
        return miView;
    }

    private void initComponents() {
        listaPublicaciones = new HashMap<>();
        correoUser = HomeActivityNavigation.getCorreoUsuario();
        addPublicationButton = miView.findViewById(R.id.addPublicationButtonProfile);
        moveFavoritePublications = miView.findViewById(R.id.miFavoritosButton);
        databaseReference = FirebaseFirestore.getInstance();
        misFavoritosFragment = new MisFavoritos();
    }

    private void consultaDatos() {
        DocumentReference documentoRef = databaseReference.collection("Users").document(correoUser);
        // Obtén el documento
        documentoRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists() && document.get("listaPublicaciones") != null) {
                        // Obtiene el valor del campo de tipo array
                        ArrayList<String> publicaciones = (ArrayList<String>) document.get("listaPublicaciones");
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
        adaptadorPublicaciones = new AdaptadorPublicaciones(miView.getContext(), listaPublicaciones);
        recyclerViewPublicaciones.setAdapter(adaptadorPublicaciones);
    }
}