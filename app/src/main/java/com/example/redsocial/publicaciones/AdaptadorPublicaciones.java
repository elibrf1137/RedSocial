package com.example.redsocial.publicaciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocial.R;

import java.util.ArrayList;

public class AdaptadorPublicaciones  extends RecyclerView.Adapter<AdaptadorPublicaciones.ViewHolder> implements View.OnClickListener{

    LayoutInflater layoutInflater;
    ArrayList<Publicacion> listaPublicacion;
    View.OnClickListener onClickListener;

    @Override
    public void onClick(View v) {
        if(onClickListener!=null){
            onClickListener.onClick(v);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText contenido;
        CheckBox favorita;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contenido = itemView.findViewById(R.id.descTextView);
            favorita = itemView.findViewById(R.id.meGustaCheckBox);

        }
    }
}
