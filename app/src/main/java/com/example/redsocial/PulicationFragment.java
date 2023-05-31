package com.example.redsocial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class PulicationFragment extends Fragment {

    private FirebaseFirestore miBaseDatos;
    Button addButton;
    EditText mensajeEditText;
    View myView;

    public PulicationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView =inflater.inflate(R.layout.fragment_pulication,container,false);
        addButton = myView.findViewById(R.id.addPublicationButton);
        mensajeEditText = myView.findViewById(R.id.pulicationEditTextText);
        miBaseDatos = FirebaseFirestore.getInstance();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensejeUser = mensajeEditText.getText().toString();


            }
        });

        return inflater.inflate(R.layout.fragment_pulication, container, false);
    }

    private void createPublication(String mensajeUser, Usuarios user){

    }

}