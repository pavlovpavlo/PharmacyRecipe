package com.sklad.er71.presentation.change_password;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sklad.er71.R;
import com.sklad.er71.Repository.Repository;
import com.sklad.er71.ViewModel.ViewModelChangePassword;
import com.sklad.er71.ViewModel.ViewModelPassRecover;
import com.sklad.er71.presentation.login.LoginActivity;
import com.sklad.er71.presentation.password_recover.PasswordRecoverActivity;
import com.sklad.er71.util.Event;
import com.sklad.er71.util.LiveDataUtils;

public class ChangePasswordActivity extends AppCompatActivity {
    ViewModelChangePassword viewModelChangePassword;
    Button btn_login;
    EditText login, pass, new_pas;
    ProgressBar login_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        viewModelChangePassword = ViewModelProviders.of(this).get(ViewModelChangePassword.class);

        initView();
        initLis();
    }

    private void initLis() {
        btn_login.setOnClickListener(view -> {
            login_bar.setVisibility(View.VISIBLE);
            btn_login.setEnabled(false);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
            AuthCredential credential = EmailAuthProvider
                    .getCredential(login.getText().toString(), pass.getText().toString());

// Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(new_pas.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Пароль успешно изменен", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Не верный пароль!", Toast.LENGTH_LONG).show();
                                            btn_login.setEnabled(true);
                                            login_bar.setVisibility(View.GONE);

                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Не верный пароль!", Toast.LENGTH_LONG).show();
                                btn_login.setEnabled(true);
                                login_bar.setVisibility(View.GONE);

                            }
                        }
                    });


//            viewModelChangePassword.getRepository(login.getText().toString(),pass.getText().toString()
//                    ,new_pas.getText().toString()).removeObservers(this);
//
//
//            viewModelChangePassword.getRepository(login.getText().toString(),pass.getText().toString()
//            ,new_pas.getText().toString()).observe(this, RecoverResponse -> {
//
//                if (RecoverResponse.getContentIfNotHandled()) {
//                    viewModelChangePassword.getRepository(login.getText().toString(),pass.getText().toString()
//                            ,new_pas.getText().toString()).removeObservers(this);
//                    Toast.makeText(this, "Пароль успешно изменен", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    viewModelChangePassword.getRepository(login.getText().toString(),pass.getText().toString()
//                            ,new_pas.getText().toString()).removeObservers(this);
//                    Toast.makeText(this, "Не верный пароль!", Toast.LENGTH_LONG).show();
//                    btn_login.setEnabled(true);
//                    login_bar.setVisibility(View.GONE);
//                }
//            });


        });

        btn_login.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                return false;
            }
        });

    }

    private void initView() {
        btn_login = findViewById(R.id.button_login);
        login = findViewById(R.id.text_login);
        pass = findViewById(R.id.text_password);
        new_pas = findViewById(R.id.text_password2);
        login_bar = findViewById(R.id.login_bar);
        login_bar.setVisibility(View.GONE);

    }
}