package com.example.onlinepharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ForgotPasswordActivity";
    public FirebaseAuth mAuth;
    TextView resetPasswordButton;
    EditText emailTextInput;
    ImageView backtologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        changeStatusBarColor();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        emailTextInput = findViewById(R.id.fpEmailTextInput);
        resetPasswordButton = findViewById(R.id.resentVerificationButton);
        backtologin = findViewById(R.id.backtologin);
        mAuth = FirebaseAuth.getInstance();


        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailTextInput.getText().toString().contentEquals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else {


                    mAuth.sendPasswordResetEmail(emailTextInput.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) Log.d(TAG, "Email sent.");


                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            ForgotPasswordActivity.this);

                                    // set title
                                    alertDialogBuilder.setTitle("Reset Password");

                                    // set dialog message
                                    alertDialogBuilder.setMessage("A Reset Password Link Is Sent To Your Registered EmailID");
                                    alertDialogBuilder
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            ForgotPasswordActivity.this.finish();

                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();


                                }
                            });

                }
            }
        });

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                ForgotPasswordActivity.this.finish();
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
}