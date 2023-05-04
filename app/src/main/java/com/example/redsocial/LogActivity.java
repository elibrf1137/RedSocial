 package com.example.redsocial;

 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;

 import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
 import com.google.android.gms.common.api.GoogleApiClient;
 import com.google.android.gms.common.SignInButton;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;

 import androidx.appcompat.app.AppCompatActivity;

 public class LogActivity extends AppCompatActivity {

     EditText userEmail;
     EditText userPsw;
     Button loginButton;

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
     }

     public void crearCuenta(View view){
         Intent crearCuenta = new Intent(this, RegistroActivity.class);
         startActivity(crearCuenta);
     }

     public void iniciarSesion(View view){
        if(!userEmail.getText().toString().isEmpty() && !userPsw.getText().toString().isEmpty())
         FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail.getText().toString(),userPsw.getText().toString());
     }
}