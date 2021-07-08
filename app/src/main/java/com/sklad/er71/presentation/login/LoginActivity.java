package com.sklad.er71.presentation.login;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sklad.er71.presentation.menu.MenuActivity;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.presentation.password_recover.PasswordRecoverActivity;
import com.sklad.er71.util.LocalSharedUtil;
import com.sklad.er71.util.Util;

public class LoginActivity extends BaseActivity {

    private EditText email;
    private EditText pass;
    private LinearLayout auth;
    private FirebaseFirestore firestore;
    private String userId;
    private TextView text_password_recover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        text_password_recover= findViewById(R.id.text_password_recover);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        auth = findViewById(R.id.auth);
        firestore = FirebaseFirestore.getInstance();

        auth.setOnClickListener(v -> {
            if(checkData())
                authorization();
        });
        text_password_recover.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, PasswordRecoverActivity.class));
        });

    }

    private boolean checkData(){
        if(!Util.isValidEmail(email.getText().toString().trim())){
            showError("Неправильный формат электронной почты");
            return false;
        }
        if(pass.getText().toString().trim().length()<6){
            showError("Пароль должен быть не менее 6 символов");
            return false;
        }

        return true;
    }

    private void authorization(){
        String emailText = email.getText().toString().trim();
        String passText = pass.getText().toString().trim();
        startLoader();

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        loadSnilsFromFirestore();
                    } else {
                        stopLoader();
                        showError("Ошибка регистрации. Попробуйте пожалуйста позже.");
                    }
                })
                .addOnFailureListener(e -> {
                    stopLoader();
                    showError(e.getMessage());
                });
    }

    private void loadSnilsFromFirestore(){
        firestore.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String snils = task.getResult().getString("snils");

                        LocalSharedUtil.setSnilsParameter(snils, getApplicationContext());
                        stopLoader();

                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                        finish();
                    } else {
                        stopLoader();
                        showError("Ошибка регистрации. Попробуйте пожалуйста позже.");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                stopLoader();
                showError(e.getMessage());
            }
        });
    }
}