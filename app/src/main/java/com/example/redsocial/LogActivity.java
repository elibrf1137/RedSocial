 package com.example.redsocial;

 import android.app.Dialog;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;

 import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
 import com.google.android.gms.common.api.GoogleApiClient;
 import com.google.android.gms.common.SignInButton;
 import com.google.android.gms.tasks.OnCompleteListener;
 import com.google.android.gms.tasks.OnSuccessListener;
 import com.google.android.gms.tasks.Task;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.auth.SignInMethodQueryResult;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.firestore.DocumentSnapshot;
 import com.google.firebase.firestore.FirebaseFirestore;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AlertDialog;
 import androidx.appcompat.app.AppCompatActivity;

 import java.util.Objects;

 public class LogActivity extends AppCompatActivity {
     EditText userEmail;
     EditText userPsw;
     Button loginButton;
     Usuarios user;
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
        if(!userEmail.getText().toString().isEmpty() && !userPsw.getText().toString().isEmpty())
         FirebaseAuth.getInstance().fetchSignInMethodsForEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
             @Override
             public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                 if (task.isSuccessful()){
                    SignInMethodQueryResult result = task.getResult();
                    if(!Objects.requireNonNull(result.getSignInMethods()).isEmpty()){
                        Intent intentHomeActivity = new Intent(getApplicationContext(),HomeActivityNavigation.class);
                        //miBaseDatos.collection("Users").document(userEmail.getText().toString()).get().addOnSuccessListener(OnCompleteListene);
                        startActivity(intentHomeActivity);
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

}