package com.sg.hackamu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sg.hackamu.databinding.ActivityFacultyLogInBinding;

public class FacultyLogIn extends AppCompatActivity {
    private Button signupButton;
    private Button loginButton;
    private ProgressBar progressBar;
    private EditText email;
    private EditText password;
    private TextView forgotpass;
    private ActivityFacultyLogInBinding loginBinding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private  FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_log_in);

        loginBinding= DataBindingUtil.setContentView(FacultyLogIn.this,R.layout.activity_faculty_log_in);
        loginBinding.setClickHandlers(new LoginActivityClickHandlers());
        getSupportActionBar().setTitle("FACULTY LOG IN");
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser=firebaseAuth.getCurrentUser();
                Log.d("Auth State","Auth State Changed");

            }
        };
        signupButton=loginBinding.signupbutton;
        progressBar=loginBinding.progressBar1;
        loginButton=loginBinding.loginButton;
        email=loginBinding.email;
        password=loginBinding.password;
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
        {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
    public class LoginActivityClickHandlers{
        public void onLoginButtonClicked(View view) {
            if (email.getText().toString().trim().length() != 0 && password.getText().toString().trim().length() != 0) {

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(FacultyLogIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Intent i = new Intent(FacultyLogIn.this, MainActivityFaculty.class);
                            startActivity(i);
                            FacultyLogIn.this.finish();
                        } else {
                        }
                    }
                });
            }
            else
            {
                Toast.makeText(FacultyLogIn.this,"Error! Empty Inputs",Toast.LENGTH_SHORT).show();
            }
        }

        public void onSignUpButtonClicked (View view){
            startActivity(new Intent(FacultyLogIn.this, FacultySignUp.class));

        }

        public void onForgotPasswordClicked (View view)
        {

        }
        public void onLoginAsFacultyClicked (View view)
        {
            startActivity(new Intent(FacultyLogIn.this, LauncherActivity.class));
            FacultyLogIn.this.finish();
        }
    }
}
