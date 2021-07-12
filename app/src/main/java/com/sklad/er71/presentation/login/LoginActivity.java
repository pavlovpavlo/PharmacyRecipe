package com.sklad.er71.presentation.login;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sklad.er71.presentation.menu.MenuActivity;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.presentation.password_recover.PasswordRecoverActivity;
import com.sklad.er71.presentation.reg.RegistrationActivity;
import com.sklad.er71.util.LocalSharedUtil;
import com.sklad.er71.util.Util;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends BaseActivity {

    private LinearLayout dialogCheckCode;
    private EditText code;
    private LinearLayout checkCode;

    private EditText phone;
    private FirebaseFirestore firestore;
    private String userId;
    private String verificationCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.w("onVerificationCompleted", "onVerificationCompleted");
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            stopLoader();

            if (e instanceof FirebaseAuthInvalidCredentialsException)
                showError("Неверный код подтверждения");
            else {
                if (e instanceof FirebaseTooManyRequestsException)
                    showError("Превышен лимит запросов на верификацию");
                else
                    showError("Ошибка верификации номера телефона");
            }
        }

        @Override
        public void onCodeSent(String verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            verificationCode = verificationId;
            Toast.makeText(LoginActivity.this, "На Ваш телефон отправлено смс", Toast.LENGTH_SHORT).show();
            stopLoader();
            dialogCheckCode.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        dialogCheckCode = findViewById(R.id.dialog_set_code);
        code = findViewById(R.id.code);
        checkCode = findViewById(R.id.check_code);

        phone = findViewById(R.id.phone);
        LinearLayout auth = findViewById(R.id.auth);
        LinearLayout reg = findViewById(R.id.reg);
        firestore = FirebaseFirestore.getInstance();

        checkCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkVerification()) {
                    String otp = code.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        auth.setOnClickListener(v -> {
            if (checkData())
                authorization();
        });
        reg.setOnClickListener(v ->
                startActivity(new Intent(this, RegistrationActivity.class)));

    }

    private boolean checkVerification() {
        if (code.getText().toString().trim().length() < 1) {
            showError("Код подтверждения не может быть пустым");
            return false;
        } else
            return true;
    }

    private boolean checkData() {
        if (phone.getText().toString().trim().length() < 1) {
            showError("Поле телефона не может быть пустым");
            return false;
        }

        return true;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        startLoader();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        stopLoader();
                        if (task.isSuccessful()) {
                            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            loadSnilsFromFirestore();
                        }
                    }
                }).addOnFailureListener(e -> {
            e.printStackTrace();
            stopLoader();
            if (e instanceof FirebaseAuthInvalidCredentialsException)
                showError("Неверный код подтверждения");
            else
                showError("Ошибка регистрации. Попробуйте пожалуйста позже.");
        });
    }

    private void authorization() {
        String phoneText = phone.getText().toString().trim();

        startLoader();

        startPhoneNumberVerification("+"+phoneText.replace("+", ""));
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void loadSnilsFromFirestore() {
        firestore.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String snils = task.getResult().getString("snils");
                        String name = task.getResult().getString("name");
                        String surname = task.getResult().getString("surname");
                        String birthday = task.getResult().getString("birthday");

                        LocalSharedUtil.setSnilsParameter(snils, getApplicationContext());
                        LocalSharedUtil.setNameParameter(name, getApplicationContext());
                        LocalSharedUtil.setSurnameParameter(surname, getApplicationContext());
                        LocalSharedUtil.setBirthdayParameter(birthday, getApplicationContext());
                        stopLoader();
                        Toast.makeText(getApplicationContext(), "Авторизация успешна", Toast.LENGTH_LONG).show();
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