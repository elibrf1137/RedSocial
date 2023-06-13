package com.example.redsocial.usuarios;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.redsocial.HomeActivityNavigation;
import com.example.redsocial.R;
import com.example.redsocial.usuarios.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LogActivity extends AppCompatActivity {
    EditText userEmail;
    EditText userPsw;
    Button loginButton;
    private FirebaseFirestore miBaseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private void initComponents(){
        userEmail = findViewById(R.id.usernameEditText);
        userPsw = findViewById(R.id.pswEditText);
        loginButton = findViewById(R.id.logButton);
        miBaseDatos = FirebaseFirestore.getInstance();
    }

    public void crearCuenta(View view){
        Intent crearCuenta = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(crearCuenta);
    }

    public void iniciarSesion(View view){
        if(!userEmail.getText().toString().trim().isEmpty() && !userPsw.getText().toString().trim().isEmpty())
            FirebaseAuth.getInstance().fetchSignInMethodsForEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if (task.isSuccessful()){
                        Bundle datosUsuario = new Bundle();
                        SignInMethodQueryResult result = task.getResult();
                        if(!Objects.requireNonNull(result.getSignInMethods()).isEmpty()){
                            Log.d("Correo del usuario: ",userEmail.getText().toString());
                            datosUsuario.putString("correoUsuario",userEmail.getText().toString());
                            Intent intentHomeActivity = new Intent(getApplicationContext(), HomeActivityNavigation.class);
                            intentHomeActivity.putExtras(datosUsuario);
                            startActivity(intentHomeActivity);
                        }else{
                            dameUnSegundo();
                        }
                    }else{
                        userNoEncontrado();
                    }
                }
            });
    }
    private void userNoEncontrado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("No se ha podido encontrar tu usuario, pruebe a crear uno");
        builder.setPositiveButton("OK",null);
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dameUnSegundo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upss");
        builder.setMessage("Espera un segundo por favor");
        builder.setPositiveButton("OK",null);
        Dialog dialog = builder.create();
        dialog.show();
    }
}