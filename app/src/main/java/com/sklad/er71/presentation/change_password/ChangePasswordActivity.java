package com.sklad.er71.presentation.change_password;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.presentation.login.LoginActivity;
import com.sklad.er71.util.Util;

public class ChangePasswordActivity extends BaseActivity {

    LinearLayout btn_login;
    EditText login, pass, new_pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
        initLis();
    }

    private void initLis() {
        btn_login.setOnClickListener(view -> {
            if (checkData())
                changePassword();
        });

    }

    private boolean checkData() {
        if (!Util.isValidEmail(login.getText().toString().trim())) {
            showError("Неправильный формат электронной почты");
            return false;
        }
        if (pass.getText().toString().trim().length() < 6) {
            showError("Не верный пароль!");
            return false;
        }
        if (new_pas.getText().toString().trim().length() < 6) {
            showError("Пароль должен быть не менее 6 символов");
            return false;
        }

        return true;
    }

    private void changePassword() {
        btn_login.setEnabled(false);

        startLoader();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(login.getText().toString().trim(), pass.getText().toString().trim());

        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(new_pas.getText().toString()).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Пароль успешно изменен", Toast.LENGTH_LONG).show();
                                /*Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                startActivity(intent);*/
                                finish();
                            } else {
                                showError("Ошибка смены пароля");
                                btn_login.setEnabled(true);
                                stopLoader();

                            }
                        }).addOnFailureListener(e -> {
                            showError(e.getMessage());
                            btn_login.setEnabled(true);
                            stopLoader();
                        });
                    }
                }).addOnFailureListener(e -> {
            if (e instanceof FirebaseAuthInvalidCredentialsException)
                showError("Неверный email и/или пароль");
            else
                showError("Ошибка смены пароля");
            btn_login.setEnabled(true);
            stopLoader();
        });
    }

    private void initView() {
        btn_login = findViewById(R.id.change);
        login = findViewById(R.id.text_login);
        pass = findViewById(R.id.text_password);
        new_pas = findViewById(R.id.text_password2);
    }
}