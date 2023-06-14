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
import com.google.android.gms.tasks.OnCompleteListener;
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

public class AdaptadorFavoritos extends RecyclerView.Adapter<AdaptadorFavoritos.ViewHolder> implements View.OnClickListener {

    LayoutInflater layoutInflater;
    HashMap<String, Object> listaPublicacion;
    Button corazonButton;
    View.OnClickListener onClickListener;
    FirebaseFirestore miBaseDatos;
    String correoUser = HomeActivityNavigation.getCorreoUsuario();
    View miView;

    private FirebaseFirestore databaseReference;


    public AdaptadorFavoritos(Context context, HashMap listaPublicacion) {
        this.layoutInflater = LayoutInflater.from(context);
        this.listaPublicacion = listaPublicacion;
        if (!listaPublicacion.isEmpty()) {
            for (String key : this.listaPublicacion.keySet()) {
                Log.d("Datos que llegan al adaptador", (String) listaPublicacion.get(key));
            }
        }
    }


    @NonNull
    @Override
    public AdaptadorFavoritos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        miView = layoutInflater.inflate(R.layout.visualizar_favoritos, parent, false);
        miView.setOnClickListener(this);
        return new AdaptadorFavoritos.ViewHolder(miView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<String> listAux = new ArrayList<>();
        String contenidoPublicacion;
        //listaPublicacion = new HashMap<>();
        correoUser = HomeActivityNavigation.getCorreoUsuario();
        databaseReference = FirebaseFirestore.getInstance();

        for (String key : listaPublicacion.keySet()) {
            listAux.add(listaPublicacion.get(key).toString());
        }
        contenidoPublicacion = listAux.get(position);
        holder.contenido.setText(contenidoPublicacion);
        holder.contenido.setEnabled(false);

        corazonButton = holder.corazon;
        corazonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPublicacion(contenidoPublicacion);
            }
        });
    }

    public void eliminarPublicacion(String publicacion) {
        miBaseDatos = FirebaseFirestore.getInstance();
        DocumentReference docRefUser = miBaseDatos.collection("Users").document(correoUser);

        // Crear un nuevo Map para almacenar los campos y valores actualizados
        HashMap<String, Object> updates = new HashMap<>();
        for (String key : listaPublicacion.keySet()) {
            if (!listaPublicacion.get(key).equals(publicacion)) {
                // Agregamos la publicación al nuevo Map para mantenerla
                updates.put(key, listaPublicacion.get(key));
            }
        }

        // Actualizar el documento con el nuevo Map de actualizaciones
        docRefUser.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(miView.getContext(), "Publicación eliminada de favoritos", Toast.LENGTH_SHORT).show();
                    listaPublicacion = updates; // Actualizar la lista local
                    notifyDataSetChanged();
                } else {
                    // Ocurrió un error al actualizar el documento
                    Toast.makeText(miView.getContext(), "Error al eliminar la publicación de favoritos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return listaPublicacion.size();
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contenido;
        Button corazon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contenido = itemView.findViewById(R.id.descFavoritosTextView);
            corazon = itemView.findViewById(R.id.corazonButtonFavoritos);
        }
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
                                listaPublicacion.put(correoUser+(i), publicaciones.get(i));
                            }
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
}
