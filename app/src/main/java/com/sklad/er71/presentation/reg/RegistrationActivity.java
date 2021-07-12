package com.sklad.er71.presentation.reg;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sklad.er71.presentation.menu.MenuActivity;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.util.LocalSharedUtil;
import com.sklad.er71.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class RegistrationActivity extends BaseActivity {

    private LinearLayout dialogCheckCode;
    private EditText code;
    private LinearLayout checkCode;

    private EditText phone;
    private MaskedEditText snils;
    private MaskedEditText birthday;
    private EditText name;
    private EditText surname;
    private LinearLayout reg;
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
            Toast.makeText(RegistrationActivity.this, "На Ваш телефон отправлено смс", Toast.LENGTH_SHORT).show();
            stopLoader();
            dialogCheckCode.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
    }

    private void initViews() {
        dialogCheckCode = findViewById(R.id.dialog_set_code);
        code = findViewById(R.id.code);
        checkCode = findViewById(R.id.check_code);

        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.second_name);
        birthday = findViewById(R.id.birthday);
        snils = findViewById(R.id.snils);
        reg = findViewById(R.id.reg);
        firestore = FirebaseFirestore.getInstance();

        reg.setOnClickListener(v -> {
            if (checkData())
                registration();
        });

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
        if (name.getText().toString().trim().length() < 1) {
            showError("Поле имени не может быть пустым");
            return false;
        }
        if (surname.getText().toString().trim().length() < 1) {
            showError("Поле фамилии не может быть пустым");
            return false;
        }
        if (birthday.getText().toString().trim().length() < 10) {
            showError("Неверный формат даты рождения");
            return false;
        }
        if (snils.getText().toString().trim().length() < 13) {
            showError("Неправильный СНИЛС");
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
                            saveUserSnils();
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

    private void registration() {
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

    private void saveUserSnils() {
        String snilsText = snils.getText().toString().trim();
        String nameText = name.getText().toString().trim();
        String surnameText = surname.getText().toString().trim();
        String birthdayText = birthday.getText().toString().trim();
        DocumentReference documentReference =
                firestore.collection("users").document(userId);

        Map<String, Object> user = new HashMap<>();
        user.put("snils", snilsText);
        user.put("name", nameText);
        user.put("surname", surnameText);
        user.put("birthday", birthdayText);

        documentReference.set(user).
                addOnSuccessListener(aVoid -> {
                    stopLoader();
                    LocalSharedUtil.setSnilsParameter(snilsText, getApplicationContext());
                    LocalSharedUtil.setNameParameter(nameText, getApplicationContext());
                    LocalSharedUtil.setSurnameParameter(surnameText, getApplicationContext());
                    LocalSharedUtil.setBirthdayParameter(birthdayText, getApplicationContext());
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