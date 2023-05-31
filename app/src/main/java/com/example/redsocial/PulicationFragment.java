package com.example.redsocial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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
                //Tienes que conseguir obtener el usuario desde la case login y para ello debes recoger los datos de la base de datos desde dicha clase
                //createPublication(mensejeUser, );
            }
        });

        return inflater.inflate(R.layout.fragment_pulication, container, false);
    }

    private void createPublication(String mensajeUser, Usuarios user){
        user.getListaPublicaciones().add(new Publicacion(mensajeUser));
        HashMap <String,Usuarios> listaActualizada = new HashMap<>();
        listaActualizada.put(user.getCorreo(),user);
        miBaseDatos.collection("Users").document(user.getCorreo().toString()).set(listaActualizada);
    }

}