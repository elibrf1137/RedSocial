package com.example.redsocial.publicaciones;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocial.HomeActivityNavigation;
import com.example.redsocial.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdaptadorPublicaciones  extends RecyclerView.Adapter<AdaptadorPublicaciones.ViewHolder> implements View.OnClickListener{

    LayoutInflater layoutInflater;
    HashMap<String, String> listaPublicacion;
    Button corazonButton;
    View.OnClickListener onClickListener;
    FirebaseFirestore miBaseDatos;
    String correoUser = HomeActivityNavigation.getCorreoUsuario();
    View miView;



    public AdaptadorPublicaciones (Context context,HashMap listaPublicacion){
        this.layoutInflater = LayoutInflater.from(context);
        this.listaPublicacion = listaPublicacion;
        if(!listaPublicacion.isEmpty()){
            for(String key: this.listaPublicacion.keySet()){
                Log.d("Datos que llegan al adaptador", (String) listaPublicacion.get(key));
            }
        }
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        miView = layoutInflater.inflate(R.layout.visualizar_publiacion,parent,false);
        miView.setOnClickListener(this);
        return new ViewHolder(miView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<String> listAux = new ArrayList<>();
        String contenidoPublicacion;
        for (String key: listaPublicacion.keySet()) {
            listAux.add(listaPublicacion.get(key));
        }
        contenidoPublicacion = listAux.get(position);
        holder.contenido.setText(contenidoPublicacion);
        holder.contenido.setEnabled(false);

        corazonButton = holder.corazon;
        corazonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarPublicaciones(contenidoPublicacion);
            }
        });
    }

    public void agregarPublicaciones(String publicacion){

        // Obtén una referencia al documento

        miBaseDatos = FirebaseFirestore.getInstance();
        DocumentReference docRefUser = miBaseDatos.collection("Users").document(correoUser);
        //CollectionReference collectionRefPublication = miBaseDatos.collection("Favoritos");

        // Actualiza el array en el documento
        docRefUser.update("Favoritos", FieldValue.arrayUnion(publicacion)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Firestore", "Elemento añadido al array correctamente");
                Toast.makeText(miView.getContext(),"Publicación añadida a favoritos",Toast.LENGTH_SHORT).show();
                Map<String,Object> publicacionAnhadida = new HashMap<>();
                publicacionAnhadida.put(correoUser+publicacionAnhadida,publicacion);
                //collectionRefPublication.add(publicacionAnhadida);
            }

        });
    }


    @Override
    public int getItemCount() {
        return listaPublicacion.size();
    }

    @Override
    public void onClick(View v) {
        if(onClickListener!=null){
            onClickListener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contenido;
        Button corazon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contenido = itemView.findViewById(R.id.descTextView);
            corazon = itemView.findViewById(R.id.corazonButton);
        }
    }
}
