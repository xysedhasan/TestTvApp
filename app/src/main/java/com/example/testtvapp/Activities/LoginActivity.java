package com.example.testtvapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testtvapp.Model.AppRepository;
import com.example.testtvapp.Model.Prefrences;
import com.example.testtvapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    Button submit;
    EditText emaiEt, passwordEt;
    String email, password;
    FirebaseAuth firebaseAuth;
    ProgressBar pbar;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }

    private void validate() {
        if (TextUtils.isEmpty(emaiEt.getText().toString())) {
            emaiEt.setError("Username required");
            return;
        }

        if (TextUtils.isEmpty(passwordEt.getText().toString())) {
            passwordEt.setError("Password required");
            return;
        }


        email = emaiEt.getText().toString();
        password = passwordEt.getText().toString();
        loginuser(email, password);
    }

    private void loginuser(String email, String password) {
        pbar.setVisibility(View.VISIBLE);
        AppRepository.login(getApplicationContext(), email, password, (status, err) -> {

            pbar.setVisibility(View.GONE);
            if (status) {
                AppRepository.updatelogin();
                Prefrences.setisLoggedin(getApplicationContext(), true);
                Intent intent = new Intent(LoginActivity.this, ChooseScreenActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void init() {
        if (Prefrences.isLoggedin(getApplicationContext())) {
            Intent intent = new Intent(LoginActivity.this, ChooseScreenActivity.class);
            startActivity(intent);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.continuerltv);
        emaiEt = findViewById(R.id.emailet);
        passwordEt = findViewById(R.id.passwordet);
        pbar = findViewById(R.id.progressbr);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}