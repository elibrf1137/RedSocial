package com.example.redsocial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private EditText correoUser;
    private EditText nombreUser;
    private EditText pswUser;
    private EditText confirmPswUser;
    private Button registroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        initComponents();
    }

    private void initComponents(){
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Usuarios");
        correoUser = findViewById(R.id.registroEmaileditText);
        nombreUser = findViewById(R.id.registroUsernameEditText);
        pswUser = findViewById(R.id.registroPasswordEditText);
        confirmPswUser = findViewById(R.id.registroConfirmPasswordEditText);
        registroButton = findViewById(R.id.registerButton);
    }

    public void createUser(View v){
       //abaseReference referenciaBD = database.getReference("Usuarios").child(correoUser.getText().toString());
       //erenciaBD.addListenerForSingleValueEvent(new ValueEventListener() {
       // @Override
       // public void onDataChange(DataSnapshot dataSnapshot) {
       //     // Aquí puedes obtener el usuario de la base de datos
       //     Usuarios usuario = dataSnapshot.getValue(Usuarios.class);
       //     if(usuario == (null)){
       //         if(confirmPswUser.getText().equals(pswUser.getText())){
       //             referenciaBD.child(correoUser.getText().toString()).setValue(new Usuarios(correoUser.getText().toString(),nombreUser.getText().toString(),pswUser.getText().toString()));
       //         }else{
       //             Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
       //         }
       //     }else{
       //         Toast.makeText(getApplicationContext(),"Este usuario ya existe",Toast.LENGTH_LONG).show();
       //     }
    }
}