 package com.example.redsocial;

 import android.annotation.SuppressLint;
 import android.os.Bundle;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
 import com.google.android.gms.auth.api.Auth;
 import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
 import com.google.android.gms.auth.api.signin.GoogleSignInApi;
 import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
 import com.google.android.gms.common.api.GoogleApiClient;
 import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
 import com.google.android.gms.common.ConnectionResult;
 import com.google.android.gms.common.SignInButton;

public class MainActivity extends AppCompatActivity implements OnConnectionFailedListener {

    private SignInButton googleSignInImage;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        initComponents();
    }


    private void initComponents(){
        googleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this,this).
                addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        googleSignInImage = findViewById(R.id.imageView);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}