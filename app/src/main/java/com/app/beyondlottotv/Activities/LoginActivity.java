package com.app.beyondlottotv.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.beyondlottotv.Api.ApiResponseCustomtoken;
import com.app.beyondlottotv.Model.AppRepository;
import com.app.beyondlottotv.Model.LoginData;
import com.app.beyondlottotv.Model.Prefrences;
import com.app.beyondlottotv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import net.glxn.qrgen.android.QRCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    Button submit;
    EditText emaiEt, passwordEt;
    private ImageView qrCodeImageView;
    ProgressBar pbar,pbarqr;
    private FirebaseAuth mAuth;
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
        pbarqr = findViewById(R.id.progressqr);
        passwordEt = findViewById(R.id.passwordet);
        pbar = findViewById(R.id.progressbr);
        qrCodeImageView = findViewById(R.id.ivqrcodeimg);
//        String loginCode = generateLoginCode(uniqueIdentifier); // Generate the login code using a cryptographic algorithm
        String loginCode = "thisisthelogingeneratedcode";
        Picasso.get().load(QRCode.from(loginCode).withSize(250, 250).file()).into(qrCodeImageView);
        getCustomtoken();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private String generateUniqueIdentifier() {
        long timestamp = System.currentTimeMillis(); // Get the current timestamp
        return String.valueOf(timestamp);
    }

    private void getCustomtoken() {
        pbarqr.setVisibility(View.VISIBLE);
        AppRepository.getCustomTokenofLogin(getApplicationContext(), (data, status) -> {
            if (status) {
                signInwithCustomToken(data);
            }
        });
    }

    private void signInwithCustomToken(LoginData data) {
        if (data.isStatus()) {
            pbarqr.setVisibility(View.VISIBLE);
            if (!data.getToken().equals("") && data.getToken() != null){
                AppRepository.signinWithCustomToken(data.getToken(), (err, status) -> {
                    if (true) {
                        AppRepository.updatelogin();
                        Prefrences.setisLoggedin(getApplicationContext(), true);
                        Intent intent = new Intent(LoginActivity.this, ChooseScreenActivity.class);
                        startActivity(intent);
                        pbarqr.setVisibility(View.GONE);
                    }else {
                        pbarqr.setVisibility(View.GONE);
                        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                pbarqr.setVisibility(View.GONE);
            }

        }else {
            pbarqr.setVisibility(View.GONE);
        }
    }
    private void clearCustomtoken(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        LoginData loginData = new LoginData("", "", 0,false);
        db.collection("login")
                .document("thisisthelogingeneratedcode")
                .set(loginData);
    }
}