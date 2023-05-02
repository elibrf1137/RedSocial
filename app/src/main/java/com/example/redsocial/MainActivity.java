 package com.example.redsocial;

 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import com.google.android.gms.auth.api.Auth;
 import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
 import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
 import com.google.android.gms.auth.api.signin.GoogleSignInResult;
 import com.google.android.gms.common.api.GoogleApiClient;
 import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
 import com.google.android.gms.common.ConnectionResult;
 import com.google.android.gms.common.SignInButton;
 import com.google.firebase.auth.FirebaseUser;

 import androidx.appcompat.app.AppCompatActivity;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;

 public class MainActivity extends AppCompatActivity {

//    private SignInButton googleSignInImage;
//    private GoogleApiClient googleApiClient;
//    private static final int RC_SIGN_IN = 9001;
//    private GoogleSignInOptions gso;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        //gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        initComponents();
    }

     private void initComponents(){
         database = FirebaseDatabase.getInstance();
         usersRef = database.getReference("Usuarios");

//         googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
//         googleSignInImage = findViewById(R.id.imageView);
//         googleSignInImage.setSize(SignInButton.SIZE_STANDARD);
//         googleSignInImage.setColorScheme(SignInButton.COLOR_LIGHT);//Vuelve el boton transparentre
//         googleSignInImage.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 signIn();
//             }
//         });

     }

     public void crearCuenta(View view){
         Intent crearCuenta = new Intent(this, RegistroActivity.class);
         startActivity(crearCuenta);
     }

     public void iniciarSesion(View view){

     }

//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        // Handle connection failure
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//
//     private void handleSignInResult(GoogleSignInResult result) {
//         if (result.isSuccess()) {
//             GoogleSignInAccount account = result.getSignInAccount();
//             AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//             database.getInstance().signInWithCredential(credential)
//                     .addOnCompleteListener(this, task -> {
//                         if (task.isSuccessful()) {
//                             FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                             String userId = user.getUid();
//                             DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
//                             usersRef.child(userId).setValue(account);
//                         } else {
//                             // Handle error authentication here
//                         }
//                     });
//         } else {
//             // Handle error login here
//         }
//     }
}