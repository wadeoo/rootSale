package cn.edu.fzu.rootsale.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

public class ProfileActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText addressEditText;
    private Button saveButton;
    private int userId;
    private User gUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();

        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);
        userId=sharedPreferences.getInt("userId",-1);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        addressEditText = findViewById(R.id.addressEditText);
        saveButton = findViewById(R.id.saveButton);


        // 加载并展示用户信息
        loadUserProfile();

        // 设置保存按钮的点击事件
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem(R.id.menu_profile).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_cart:
                        // 处理点击购物车的逻辑
                        Intent intent1 = new Intent(ProfileActivity.this, CartActivity.class);
                        startActivity(intent1);
                        Toast.makeText(ProfileActivity.this, "购物车", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_products:
                        // 处理点击商品浏览的逻辑
                        Intent intent2 = new Intent(ProfileActivity.this, ProductActivity.class);
                        startActivity(intent2);
                        Toast.makeText(ProfileActivity.this, "商品", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_orders:
                        // 处理点击订单查看的逻辑
                        Intent intent3 = new Intent(ProfileActivity.this, OrderActivity.class);
                        startActivity(intent3);
                        Toast.makeText(ProfileActivity.this, "订单", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_profile:
                        // 处理点击个人信息的逻辑
                        Toast.makeText(ProfileActivity.this, "个人信息", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

    }

    // 加载并展示用户信息
    private void loadUserProfile() {

        // 创建 Retrofit 实例
        Retrofit retrofit = createRetrofit();

        // 创建 接口实例
        UserService userService = retrofit.create(UserService.class);

        // 发起获取菜品列表的请求
        Call<User> call = userService.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    gUser = response.body();
                    // 将用户信息展示到对应的EditText中
                    usernameEditText.setText(gUser.getUsername());
                    passwordEditText.setText(gUser.getPassword());
                    addressEditText.setText(gUser.getAddress());
                } else {
                    Toast.makeText(ProfileActivity.this, "用户数据获取失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
            }
        });



    }

    // 保存用户信息
    private void saveUserProfile() {
        // 获取用户修改后的信息
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        User user=new User(username,password,address);

        // 在这里执行保存操作，例如将用户信息更新到数据库或发送到服务器
        // 创建 Retrofit 实例
        Retrofit retrofit = createRetrofit();

        // 创建 接口实例
        UserService userService = retrofit.create(UserService.class);

        // 发起获取菜品列表的请求
        Call<Void> call = userService.changeProfile(user,userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "用户信息修改成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "用户信息修改失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
            }
        });

    }


    private Retrofit createRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
                .build();

        return retrofit;
    }

}
