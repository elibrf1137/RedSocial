package com.example.redsocial;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private void initComponents() {
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Usuarios");
        correoUser = findViewById(R.id.registroEmaileditText);
        nombreUser = findViewById(R.id.registroUsernameEditText);
        pswUser = findViewById(R.id.registroPasswordEditText);
        confirmPswUser = findViewById(R.id.registroConfirmPasswordEditText);
        registroButton = findViewById(R.id.registerButton);
    }

    public void createUser(View v) {
        if(pswUser.getText().toString().equals(confirmPswUser.getText().toString())&& !pswUser.getText().toString().isEmpty() &&!correoUser.getText().toString().isEmpty()){
            Usuarios newUser = new Usuarios(correoUser.getText().toString(),nombreUser.getText().toString(),pswUser.getText().toString());
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(newUser.getCorreo(),newUser.getPsw()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Usuario creado correctamente",Toast.LENGTH_SHORT).show();
                        //userCreado();
                    }else{
                        userNoCreado();
                    }
                }
            });
        }
    }
    private void userNoCreado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("No se ha podido añadir tu usuario");
        builder.setPositiveButton("OK",null);
        Dialog dialog = builder.create();
        dialog.show();
    }

//------------------------------------------------------------------------
    //private void userCreado(){
    //    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    //    builder.setTitle("Correcto!");
    //    builder.setMessage("Se ha añadido tu usuario correctamente");
    //    builder.setPositiveButton("Aceptar",null);
    //    Dialog dialog = builder.create();
    //    dialog.show();
    //}
}
