package com.sklad.er71.presentation.change_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.sklad.er71.presentation.menu.MenuActivity;
import com.sklad.er71.util.Util;

public class ChangePasswordActivity extends Fragment {

    LinearLayout btn_login;
    EditText login, pass, new_pas;
    private MenuActivity mainActivity;
    private View root;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            mainActivity = (MenuActivity) context;
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_change_password, container, false);

        initView();
        initLis();

        return root;
    }

    private void initLis() {
        btn_login.setOnClickListener(view -> {
            if (checkData())
                changePassword();
        });

    }

    private boolean checkData() {
        if (!Util.isValidEmail(login.getText().toString().trim())) {
            mainActivity.showError("Неправильный формат электронной почты");
            return false;
        }
        if (pass.getText().toString().trim().length() < 6) {
            mainActivity.showError("Не верный пароль!");
            return false;
        }
        if (new_pas.getText().toString().trim().length() < 6) {
            mainActivity.showError("Пароль должен быть не менее 6 символов");
            return false;
        }

        return true;
    }

    private void changePassword() {
        btn_login.setEnabled(false);

        mainActivity.startLoader();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(login.getText().toString().trim(), pass.getText().toString().trim());

        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(new_pas.getText().toString()).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(mainActivity, "Пароль успешно изменен", Toast.LENGTH_LONG).show();
                                /*Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                startActivity(intent);*/
                                mainActivity.onBackPressed();
                            } else {
                                mainActivity.showError("Ошибка смены пароля");
                                btn_login.setEnabled(true);
                                mainActivity.stopLoader();

                            }
                        }).addOnFailureListener(e -> {
                            mainActivity.showError(e.getMessage());
                            btn_login.setEnabled(true);
                            mainActivity.stopLoader();
                        });
                    }
                }).addOnFailureListener(e -> {
            if (e instanceof FirebaseAuthInvalidCredentialsException)
                mainActivity.showError("Неверный email и/или пароль");
            else
                mainActivity.showError("Ошибка смены пароля");
            btn_login.setEnabled(true);
            mainActivity.stopLoader();
        });
    }

    private void initView() {
        btn_login = root.findViewById(R.id.change);
        login = root.findViewById(R.id.text_login);
        pass = root.findViewById(R.id.text_password);
        new_pas = root.findViewById(R.id.text_password2);
    }
}