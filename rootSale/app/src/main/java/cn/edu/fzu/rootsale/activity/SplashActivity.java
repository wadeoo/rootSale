package cn.edu.fzu.rootsale.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.fzu.rootsale.R;


public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 3000; // 倒计时时长，单位为毫秒
    private CountDownTimer countDownTimer;

    private TextView countdownTextView;
    private Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //
        getSupportActionBar().hide();

        countdownTextView = findViewById(R.id.countdownTextView);
        skipButton = findViewById(R.id.skipButton);

        // 设置跳过按钮点击事件
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消倒计时并跳转到主Activity
                cancelCountdown();
                startLoginActivity();
            }
        });

        // 启动倒计时
        startCountdown();
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(SPLASH_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 更新倒计时文本
                long seconds = millisUntilFinished / 1000;
                countdownTextView.setText(String.valueOf(seconds));
            }

            @Override
            public void onFinish() {
                // 倒计时结束，跳转到主Activity
                startLoginActivity();
            }
        };
        countDownTimer.start();
    }

    private void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // 结束当前Activity
    }
}