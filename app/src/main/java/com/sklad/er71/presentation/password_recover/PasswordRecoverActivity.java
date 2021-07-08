package com.sklad.er71.presentation.password_recover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sklad.er71.R;
import com.sklad.er71.ViewModel.ViewModelPassRecover;
import com.sklad.er71.presentation.login.LoginActivity;
import com.sklad.er71.util.Event;

public class PasswordRecoverActivity extends AppCompatActivity {
    ViewModelPassRecover viewModelPassRecover;
    Button btn_login;
    EditText login, pass;
    ProgressBar login_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover);
        viewModelPassRecover = ViewModelProviders.of(this).get(ViewModelPassRecover.class);
        initView();
        initLis();
    }

    private void initLis() {
        btn_login.setOnClickListener(view -> {
            login_bar.setVisibility(View.VISIBLE);
            btn_login.setEnabled(false);


            FirebaseAuth.getInstance().sendPasswordResetEmail(login.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "На Ваш Email выслан новый пароль", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PasswordRecoverActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                Log.d("Send", "Email sent.");
                            } else {
                                Toast.makeText(getApplicationContext(), "Пользователя не существует!", Toast.LENGTH_LONG).show();

                                btn_login.setEnabled(true);
                                login_bar.setVisibility(View.GONE);
                            }
                        }
                    });


//           viewModelPassRecover.getRepository(login.getText().toString()).observe(this, RecoverResponse -> {
//                if (RecoverResponse.getContentIfNotHandled()) {
//                    viewModelPassRecover.getRepository(login.getText().toString()).removeObservers(this);
//                    Toast.makeText(this, "На Ваш Email выслан новый пароль", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(PasswordRecoverActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//
//                    Toast.makeText(this, "Пользователя не существует!", Toast.LENGTH_LONG).show();
//                    viewModelPassRecover.getRepository(login.getText().toString()).removeObservers(this);
//                    btn_login.setEnabled(true);
//                    login_bar.setVisibility(View.GONE);
//
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
        login_bar = findViewById(R.id.login_bar);
        login_bar.setVisibility(View.GONE);


    }
}