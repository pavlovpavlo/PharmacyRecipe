package com.sklad.er71.presentation.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.sklad.er71.R;
import com.sklad.er71.presentation.change_password.ChangePasswordActivity;
import com.sklad.er71.presentation.contact_us.ContactUsActivity;
import com.sklad.er71.presentation.login.LoginActivity;
import com.sklad.er71.presentation.menu.MenuActivity;
import com.sklad.er71.util.LocalSharedUtil;
import com.sklad.er71.util.Util;

public class ProfileActivity extends Fragment {

    private NavController controller;
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
        root = inflater.inflate(R.layout.activity_profile, container, false);

        initViews();

        return root;
    }

    private void initViews() {
        LinearLayout contact_us = root.findViewById(R.id.contact_us);
        LinearLayout quit = root.findViewById(R.id.quit);
        TextView phone = root.findViewById(R.id.phone);
        TextView name = root.findViewById(R.id.name_text);
        TextView surname = root.findViewById(R.id.surname_text);
        TextView birthday = root.findViewById(R.id.birthday_text);
        TextView snils = root.findViewById(R.id.snils_text);
        controller = NavHostFragment.findNavController(ProfileActivity.this);

        contact_us.setOnClickListener(view -> {
            controller.navigate(R.id.fragmentContactUs);
        });

        quit.setOnClickListener(view -> {
            startActivity(new Intent(mainActivity, LoginActivity.class));
            FirebaseAuth.getInstance().signOut();
            mainActivity.finish();
        });

        phone.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        snils.setText(LocalSharedUtil.getSnilsParameter(mainActivity));
        name.setText(LocalSharedUtil.getNameParameter(mainActivity));
        surname.setText(LocalSharedUtil.getSurnameParameter(mainActivity));
        birthday.setText(LocalSharedUtil.getBirthdayParameter(mainActivity));

    }
}