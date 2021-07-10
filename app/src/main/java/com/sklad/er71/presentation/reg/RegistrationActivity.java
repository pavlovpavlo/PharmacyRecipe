package com.sklad.er71.presentation.reg;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sklad.er71.presentation.menu.MenuActivity;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.util.LocalSharedUtil;
import com.sklad.er71.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class RegistrationActivity extends BaseActivity {

    private EditText email;
    private MaskedEditText snils;
    private EditText pass;
    private EditText passRepeat;
    private LinearLayout reg;
    private FirebaseFirestore firestore;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
    }

    private void initViews() {
        email = findViewById(R.id.email);
        snils = findViewById(R.id.snils);
        pass = findViewById(R.id.pass);
        passRepeat = findViewById(R.id.pass_repeat);
        reg = findViewById(R.id.reg);
        firestore = FirebaseFirestore.getInstance();

        reg.setOnClickListener(v -> {
            if(checkData())
                registration();
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
        if(!pass.getText().toString().trim().equals(passRepeat.getText().toString().trim())){
            showError("Пароли должны совпадать");
            return false;
        }
        if(snils.getText().toString().trim().length()<13){
            showError("Неправильный СНИЛС");
            return false;
        }

        return true;
    }

    private void registration() {
        String emailText = email.getText().toString().trim();
        String passText = pass.getText().toString().trim();

        startLoader();

        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        saveUserSnils();
                    } else {
                        stopLoader();
                        showError("Ошибка регистрации. Попробуйте пожалуйста позже.");
                    }
                })
                .addOnFailureListener(e -> {
                    stopLoader();
                    if (e instanceof FirebaseAuthUserCollisionException)
                    showError("Пользователь с таким email уже зарегистрирован");
                    else
                        showError("Ошибка регистрации. Попробуйте пожалуйста позже.");
                });
    }

    private void saveUserSnils() {
        String snilsText = snils.getText().toString().trim();
        DocumentReference documentReference =
                firestore.collection("users").document(userId);

        Map<String, Object> user = new HashMap<>();
        user.put("snils", snilsText);

        documentReference.set(user).
                addOnSuccessListener(aVoid -> {
                    stopLoader();
                    LocalSharedUtil.setSnilsParameter(snilsText, getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Регистрация успешна", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegistrationActivity.this, MenuActivity.class));
                    finish();
                }).
                addOnFailureListener(e -> {
                    stopLoader();
                    showError(e.getMessage());
                });
    }

}