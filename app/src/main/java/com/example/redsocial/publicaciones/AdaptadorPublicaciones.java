package com.example.redsocial.publicaciones;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocial.R;
import com.example.redsocial.fragments.HomeFragment;
import com.example.redsocial.fragments.ProfileFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class AdaptadorPublicaciones  extends RecyclerView.Adapter<AdaptadorPublicaciones.ViewHolder> implements View.OnClickListener{

    LayoutInflater layoutInflater;
    HashMap<String, String> listaPublicacion;
    View.OnClickListener onClickListener;


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
        View view = layoutInflater.inflate(R.layout.visualizar_publiacion,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
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
        CheckBox favorita;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contenido = itemView.findViewById(R.id.descTextView);
        }
    }
}
