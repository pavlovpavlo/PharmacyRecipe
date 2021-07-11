package com.sklad.er71.presentation.contact_us;

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
import androidx.recyclerview.widget.RecyclerView;

import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.presentation.menu.MenuActivity;
import com.sklad.er71.util.Util;

public class ContactUsActivity extends Fragment {

    EditText email;
    EditText title;
    EditText text;
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
        root = inflater.inflate(R.layout.activity_contact_us, container, false);

        initViews();

        return root;
    }

    private boolean checkData() {
        if (!Util.isValidEmail(email.getText().toString().trim())) {
            mainActivity.showError("Неправильный формат электронной почты");
            return false;
        }
        if (title.getText().toString().trim().length() == 0) {
            mainActivity.showError("Заголовок не должен быть пустым");
            return false;
        }
        if (text.getText().toString().trim().length() == 0) {
            mainActivity.showError("Сообщение не должно быть пустым");
            return false;
        }

        return true;
    }

    private void initViews() {
        email = root.findViewById(R.id.text_login);
        title = root.findViewById(R.id.title);
        text = root.findViewById(R.id.text);
        LinearLayout send = root.findViewById(R.id.send);

        send.setOnClickListener(v -> {
            String emailText = email.getText().toString().trim();
            String titleText = title.getText().toString().trim();
            String messageText = text.getText().toString().trim();

            if (checkData()) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailText});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, titleText);
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, messageText);


                emailIntent.setType("message/rfc822");

                try {
                    startActivity(Intent.createChooser(emailIntent,
                            "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(mainActivity,
                            "No email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}