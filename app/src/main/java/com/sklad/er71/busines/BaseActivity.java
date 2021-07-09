package com.sklad.er71.busines;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sklad.er71.R;

public class BaseActivity extends AppCompatActivity implements BasicView {

    public static int ERROR_TIME_SECOND = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public void showError(String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CountDownTimer countDownTimer;
                findViewById(R.id.error_notification).setVisibility(View.VISIBLE);
                findViewById(R.id.error_notification).setOnClickListener(v -> findViewById(R.id.error_notification).setVisibility(View.GONE));
                ((TextView) findViewById(R.id.notification_text)).setText(error);

                countDownTimer = new CountDownTimer(ERROR_TIME_SECOND * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        findViewById(R.id.error_notification).setVisibility(View.GONE);
                    }
                };
                countDownTimer.start();
            }
        });
    }

    @Override
    public void startLoader() {
        findViewById(R.id.loader).setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoader() {
        findViewById(R.id.loader).setVisibility(View.GONE);
    }

}