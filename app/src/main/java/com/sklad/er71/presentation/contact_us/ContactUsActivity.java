package com.sklad.er71.presentation.contact_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.util.Util;

public class ContactUsActivity extends BaseActivity {

    EditText email;
    EditText title;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        initViews();
    }

    private boolean checkData() {
        if (!Util.isValidEmail(email.getText().toString().trim())) {
            showError("Неправильный формат электронной почты");
            return false;
        }
        if (title.getText().toString().trim().length() > 0) {
            showError("Заголовок не должен быть пустым");
            return false;
        }
        if (text.getText().toString().trim().length() > 0) {
            showError("Сообщение не должно быть пустым");
            return false;
        }

        return true;
    }

    private void initViews() {
        email = findViewById(R.id.email);
        title = findViewById(R.id.title);
        text = findViewById(R.id.text);
        LinearLayout send = findViewById(R.id.send);

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
                    Toast.makeText(this,
                            "No email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}