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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sg.hackamu.databinding.ActivitySignUpBinding;
import com.sg.hackamu.model.User;
import com.sg.hackamu.utils.FirebaseUtils;

public class SignUpActivity extends AppCompatActivity {
    private Button signUpButton;
    private EditText email;
    public static EditText name;
    private EditText password;
    private EditText faculty_no;
    private EditText enroll_no;
    private ActivitySignUpBinding signUpBinding;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private UserProfileChangeRequest userProfileChangeRequest;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private static final String TAG = "SignUpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpBinding=DataBindingUtil.setContentView(SignUpActivity.this,R.layout.activity_sign_up);
        signUpBinding.setClickHandlers(new SignupactivityClickHandlers());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser=firebaseAuth.getCurrentUser();
                Log.d("Auth State","Auth State Changed");

            }
        };

        getSupportActionBar().setTitle("STUDENT SIGN UP");
        mFirebaseDatabase = FirebaseUtils.getDatabase();
        myRef = mFirebaseDatabase.getReference();
        signUpButton=signUpBinding.signupbuttons;
        progressBar=signUpBinding.progressBar1;
        email=signUpBinding.emails;
        name=signUpBinding.name;
        faculty_no=signUpBinding.facultyNo;
        enroll_no=signUpBinding.enrollNo;
        password=signUpBinding.passwords;
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


    public class SignupactivityClickHandlers{
        public void onSignUpButtonClicked(View v)
        {
            if(email.getText().toString().trim().length()!=0&&name.getText().toString().trim().length()!=0&&faculty_no.getText().toString().trim().length()!=0&&enroll_no.getText().toString().trim().length()!=0&&password.getText().toString().trim().length()!=0)
            {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userProfileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name.getText().toString().trim()).build();
                            firebaseUser=firebaseAuth.getCurrentUser();
                            User user=new User();
                            user.setEmail(email.getText().toString().trim());
                            userID=firebaseUser.getUid();
                            user.setUuid(userID);
                            user.setName(name.getText().toString().trim());
                            user.setFaculty_no(faculty_no.getText().toString().trim());
                            user.setEnroll_no(enroll_no.getText().toString().trim());
                            myRef.child("students").child(userID).setValue(user);
                            firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Hello", "User profile updated.");
                                    }                                }
                            });
                            progressBar.setVisibility(View.GONE);
                            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(i);
                            SignUpActivity.this.finish();
                        } else {
                        }
                    }
                });
            } else {
                Toast.makeText(SignUpActivity.this, "Error! Empty Inputs", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
