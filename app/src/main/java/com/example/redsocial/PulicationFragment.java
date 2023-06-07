package com.example.redsocial;

import static android.content.ContentValues.TAG;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class PulicationFragment extends Fragment {

    private FirebaseFirestore miBaseDatos;
    Button addButton;
    EditText mensajeEditText;
    View myView;

    String correoUser;

    public PulicationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        miBaseDatos = FirebaseFirestore.getInstance();


        if(getArguments() != null){
            correoUser = getArguments().getString("bundleCorreoUser");
        }
        Toast.makeText(getContext(),"Correo de usuario: "+ correoUser,Toast.LENGTH_SHORT);
        myView =inflater.inflate(R.layout.fragment_pulication,container,false);
        addButton = myView.findViewById(R.id.addPublicationButton);
        mensajeEditText = myView.findViewById(R.id.pulicationEditTextText);

        Toast.makeText(getContext(),"Correo de usuario:"+correoUser,Toast.LENGTH_SHORT).show();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mensajeEditText.getText().toString().isEmpty()){
                    DocumentReference docRef = miBaseDatos.collection("Users").document(correoUser);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    Log.d(TAG, "Mis datos de usuario: " + document.getData());
                                }else{
                                    Toast.makeText(getContext(),"El documento no existe",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getContext(),"Ups, ha ocurrido un error",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    String mensejeUser = mensajeEditText.getText().toString();
                    //createPublication(mensejeUser, );
                }

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