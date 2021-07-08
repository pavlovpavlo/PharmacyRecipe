package com.sklad.er71.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sklad.er71.util.Event;

public class Repository {
    private final MutableLiveData<Event<Boolean>> fbRecover = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> fbNewPas = new MutableLiveData<>();
    private static Repository newsRepository;


    public MutableLiveData<Event<Boolean>> setNewPass(String mail, String old_pas, String new_pas){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mail, old_pas);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(new_pas).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        fbNewPas.setValue(new Event<>(true));
                                    } else {
                                        fbNewPas.setValue(new  Event<>(false));

                                    }
                                }
                            });
                        } else {
                            fbNewPas.setValue(new  Event<>(false));
                        }
                    }
                });

        return fbNewPas;

    }

    public MutableLiveData<Event<Boolean>> getRecover(String email) {

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            fbRecover.setValue(new Event<>(true));
                            Log.d("Send", "Email sent.");
                        } else {
                            fbRecover.setValue(new Event<>(false));
                        }
                    }
                });

        return fbRecover;
    }
}