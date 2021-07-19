package com.example.onlinepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    public FirebaseAuth mAuth;
    EditText emailTextInput;
    EditText passwordTextInput;
    Button signInButton;
    TextView forgotPasswordButton;
    TextView sendVerifyMailAgainButton;
    //TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        changeStatusBarColor();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        emailTextInput = findViewById(R.id.editTextEmail);
        passwordTextInput = findViewById(R.id.editTextPassword);
        signInButton = findViewById(R.id.signup_button);
        forgotPasswordButton = findViewById(R.id.forgotpassword);
        sendVerifyMailAgainButton = findViewById(R.id.verifyemailagain);

        sendVerifyMailAgainButton.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailTextInput.getText().toString().contentEquals("")) {
                    Toast.makeText(LoginActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (passwordTextInput.getText().toString().contentEquals("")) {
                    Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(emailTextInput.getText().toString(), passwordTextInput.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            if (user.isEmailVerified()) {
                                                System.out.println("Email Verified : " + user.isEmailVerified());
                                                if (!user.getEmail().equals("hirushivihanga812@gmail.com")) {
                                                    Intent HomeActivity = new Intent(LoginActivity.this, MainActivity.class);
                                                    setResult(RESULT_OK, null);
                                                    startActivity(HomeActivity);
                                                    LoginActivity.this.finish();
                                                } else{
                                                    Intent AdminOrderList = new Intent(LoginActivity.this, Admin_ViewOrder_List.class);
                                                    setResult(RESULT_OK, null);
                                                    startActivity(AdminOrderList);
                                                    LoginActivity.this.finish();
                                                }

                                            } else {
                                                sendVerifyMailAgainButton.setVisibility(View.VISIBLE);
                                                Toast.makeText(LoginActivity.this, "Verify your email first", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        if (task.getException() != null) {
                                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                }
                            });


                }


            }
        });


        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent forgotPasswordActivity = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPasswordActivity);
                LoginActivity.this.finish();

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

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}