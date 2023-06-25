package cn.edu.fzu.rootsale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.fzu.rootsale.R;
import cn.edu.fzu.rootsale.constant.Constant;
import cn.edu.fzu.rootsale.model.User;
import cn.edu.fzu.rootsale.service.UserService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText addressEditText;
    private Button registerButton;
    private Button backToLoginButton;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        // 初始化视图
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        addressEditText = findViewById(R.id.addressEditText);
        registerButton = findViewById(R.id.registerButton);
        backToLoginButton = findViewById(R.id.backToLoginButton);

        // 创建 Retrofit 实例
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
                .build();


        // 创建 UserService 实例
        userService = retrofit.create(UserService.class);

        // 注册按钮点击事件
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的信息
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();

                // 创建 User 对象
                User user = new User(username, password, address);

                // 发送注册请求
                Call<Void> call = userService.register(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // 注册成功
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            // 跳转到登录界面
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish(); // 结束当前注册页面
                        } else {
                            // 注册失败
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // 请求失败
                        Toast.makeText(RegisterActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // 返回登录按钮点击事件
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到登录界面
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish(); // 结束当前注册页面
            }
        });
    }
}