package com.sklad.er71.presentation.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

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
import com.sklad.er71.presentation.profile.ProfileActivity;

public class HomeActivity extends Fragment {

    private MenuActivity mainActivity;
    private View root;
    private NavController controller;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            mainActivity = (MenuActivity) context;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_home, container, false);

        initViews();

        return root;
    }

    private void initViews() {
        controller = NavHostFragment.findNavController(HomeActivity.this);

        LinearLayout recipe = root.findViewById(R.id.recipe_btn);
        recipe.setOnClickListener(v -> {
            mainActivity.setActiveMenu(1);
            controller.navigate(R.id.fragmentRecipes);
        });
    }
}