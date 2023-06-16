package com.example.redsocial.fragments;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.redsocial.HomeActivityNavigation;
import com.example.redsocial.publicaciones.Publicacion;
import com.example.redsocial.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PulicationFragment extends Fragment {

    private FirebaseFirestore miBaseDatos;
    private Button addButton;
    private EditText mensajeEditText;
    private View myView;

    private String correoUser;

    public PulicationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        miBaseDatos = FirebaseFirestore.getInstance();

        correoUser = HomeActivityNavigation.getCorreoUsuario();

        Toast.makeText(getContext(),"Correo de usuario: "+ correoUser,Toast.LENGTH_SHORT);
        myView =inflater.inflate(R.layout.fragment_pulication,container,false);
        addButton = myView.findViewById(R.id.addPublicationButton);
        mensajeEditText = myView.findViewById(R.id.pulicationEditTextText);

        Toast.makeText(getContext(),"Correo de usuario:"+correoUser,Toast.LENGTH_SHORT).show();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mensajeEditText.getText().toString().isEmpty() && mensajeEditText !=null){
                    if(mensajeEditText.length()<250){
                        agregarPublicaciones(mensajeEditText.getText().toString());
                        Toast.makeText(getContext(),"Publicación creada",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(),"El tamaño de la publicación es demasiado grande",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(),"Upss, ha ocurrido un error",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return myView;
    }

    public void agregarPublicaciones(String publicacion){
        // Obtén una referencia al documento
        DocumentReference docRefUser = miBaseDatos.collection("Users").document(correoUser);
        CollectionReference collectionRefPublication = miBaseDatos.collection("publicaciones");

        // Actualiza el array en el documento
        docRefUser.update("listaPublicaciones", FieldValue.arrayUnion(publicacion)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Firestore", "Elemento añadido al array correctamente");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Firestore", "Error al añadir el elemento al array: " + e.getMessage());
            }
        });
        Map <String,Object> publicacionAnhadida = new HashMap<>();
        publicacionAnhadida.put(correoUser+publicacionAnhadida,publicacion);
        collectionRefPublication.add(publicacionAnhadida);
    }
}

