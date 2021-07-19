package com.example.onlinepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;


import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    public FirebaseAuth mAuth;
    Button signUpButton;
    EditText nameTextInput;
    EditText mobileNumTextInput;
    EditText signUpEmailTextInput;
    EditText signUpPasswordTextInput;
    //CheckBox agreementCheckBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        mAuth = FirebaseAuth.getInstance();

        nameTextInput = findViewById(R.id.editTextName);
        mobileNumTextInput = findViewById(R.id.editTextMobile);
        signUpEmailTextInput = findViewById(R.id.editTextEmail);
        signUpPasswordTextInput = findViewById(R.id.editTextPassword);
        signUpButton = findViewById(R.id.signup_button);
        //agreementCheckBox = findViewById(R.id.agreementCheckbox);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (signUpEmailTextInput.getText().toString().contentEquals("")) {
                    Toast.makeText(RegisterActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (signUpPasswordTextInput.getText().toString().contentEquals("")) {
                    Toast.makeText(RegisterActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                }else if (nameTextInput.getText().toString().contentEquals("")) {
                    Toast.makeText(RegisterActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }else if (mobileNumTextInput.getText().toString().contentEquals("")) {
                    Toast.makeText(RegisterActivity.this, "Mobile number cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {

                    mAuth.createUserWithEmailAndPassword(signUpEmailTextInput.getText().toString(), signUpPasswordTextInput.getText().toString()).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                try {
                                    if (user != null)
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "Email sent.");

                                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                    RegisterActivity.this);

                                                            // set title
                                                            alertDialogBuilder.setTitle("Please Verify Your EmailID");

                                                            // set dialog message
                                                            alertDialogBuilder
                                                                    .setMessage("A verification Email Is Sent To Your Registered EmailID, please click on the link and Sign in again!")
                                                                    .setCancelable(false)
                                                                    .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int id) {

                                                                            Intent signInIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                            RegisterActivity.this.finish();
                                                                        }
                                                                    });

                                                            // create alert dialog
                                                            AlertDialog alertDialog = alertDialogBuilder.create();

                                                            // show it
                                                            alertDialog.show();


                                                        }
                                                    }
                                                });

                                } catch (Exception e) {
                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                if (task.getException() != null) {
                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                }

            }
        });

    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
}