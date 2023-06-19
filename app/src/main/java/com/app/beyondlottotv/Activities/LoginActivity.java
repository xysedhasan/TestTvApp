package com.app.beyondlottotv.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.beyondlottotv.Model.AppRepository;
import com.app.beyondlottotv.Model.Prefrences;
import com.app.beyondlottotv.R;



public class LoginActivity extends AppCompatActivity {

    Button submit;
    EditText emaiEt, passwordEt;
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

        loginuser(emaiEt.getText().toString(), passwordEt.getText().toString());
    }

    private void loginuser(String email, String password) {
        pbar.setVisibility(View.VISIBLE);
        AppRepository.login(getApplicationContext(), email, password, (status, err) -> {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pbar.setVisibility(View.GONE);
                    if (status) {
                        AppRepository.updatelogin();
                        Prefrences.setisLoggedin(getApplicationContext(), true);
                        Intent intent = new Intent(LoginActivity.this, ChooseScreenActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    public void init() {
        if (Prefrences.isLoggedin(getApplicationContext())) {
            Intent intent = new Intent(LoginActivity.this, ChooseScreenActivity.class);
            startActivity(intent);
        }

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