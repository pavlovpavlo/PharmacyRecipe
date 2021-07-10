package com.sklad.er71.presentation.password_recover;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.presentation.login.LoginActivity;
import com.sklad.er71.util.Util;

public class PasswordRecoverActivity extends BaseActivity {
    LinearLayout btn_login;
    EditText login, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover);
        initView();
        initLis();
    }

    private void initLis() {
        btn_login.setOnClickListener(view -> {
            if (!Util.isValidEmail(login.getText().toString().trim())) {
                showError("Неправильный формат электронной почты");
            } else {
                recoverPass();
            }
        });

    }

    private void recoverPass() {
        startLoader();
        btn_login.setEnabled(false);

        FirebaseAuth.getInstance().sendPasswordResetEmail(login.getText().toString().trim())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "На Ваш Email выслан новый пароль", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PasswordRecoverActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        showError("Ошибка восстановления пароля");

                        btn_login.setEnabled(true);
                        stopLoader();
                    }
                }).addOnFailureListener(e -> {
            if (e instanceof FirebaseAuthInvalidUserException)
                showError("Пользователь с таким email не зарегистрирован");
            else
                showError("Ошибка восстановления пароля");

            btn_login.setEnabled(true);
            stopLoader();
        });
    }

    private void initView() {
        btn_login = findViewById(R.id.recover_pass);
        login = findViewById(R.id.text_login);
        pass = findViewById(R.id.text_password);
    }
}