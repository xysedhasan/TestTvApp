package com.example.testtvapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.testtvapp.MainActivity;
import com.example.testtvapp.Model.AppRepository;
import com.example.testtvapp.Model.Prefrences;
import com.example.testtvapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {


    Button submit;
    EditText emaiEt,passwordEt;
    String email,password;
    FirebaseAuth firebaseAuth;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        if (Prefrences.isLoggedin(getApplicationContext())) {
            Prefrences.setisLoggedin(getApplicationContext(),true);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);

                validate();

            }
        });

    }

    private void validate(){
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
        loginuser(email,password);
    }
    private  void loginuser(String email,String password){
        pbar.setVisibility(View.VISIBLE);
        AppRepository.login(getApplicationContext(),email,password,(status,err)->{

            pbar.setVisibility(View.GONE);
            if (status){
                Prefrences.setisLoggedin(getApplicationContext(),true);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void init(){
        firebaseAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.continuerltv);
        emaiEt = findViewById(R.id.emailet);
        passwordEt = findViewById(R.id.passwordet);
        pbar = findViewById(R.id.progressbr);
    }
}