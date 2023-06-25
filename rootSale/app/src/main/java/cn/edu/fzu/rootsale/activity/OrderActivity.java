package cn.edu.fzu.rootsale.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import cn.edu.fzu.rootsale.R;
import cn.edu.fzu.rootsale.adapter.OrderAdapter;
import cn.edu.fzu.rootsale.constant.Constant;
import cn.edu.fzu.rootsale.model.Orda;
import cn.edu.fzu.rootsale.service.OrdaService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Orda> orderList;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().hide();

        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);
        userId=sharedPreferences.getInt("userId",-1);

        // 初始化订单列表和适配器
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);

        // 设置RecyclerView
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(orderAdapter);

        //加载订单数据
        loadOrderData();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem(R.id.menu_orders).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_cart:
                        // 处理点击购物车的逻辑
                        Intent intent1 = new Intent(OrderActivity.this, CartActivity.class);
                        startActivity(intent1);
                        Toast.makeText(OrderActivity.this, "购物车", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_products:
                        // 处理点击商品浏览的逻辑
                        Intent intent2 = new Intent(OrderActivity.this, ProductActivity.class);
                        startActivity(intent2);
                        Toast.makeText(OrderActivity.this, "商品", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_orders:
                        // 处理点击订单查看的逻辑
                        Toast.makeText(OrderActivity.this, "订单", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_profile:
                        Intent intent3 = new Intent(OrderActivity.this, ProfileActivity.class);
                        startActivity(intent3);
                        // 处理点击个人信息的逻辑
                        Toast.makeText(OrderActivity.this, "个人信息", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

    }

    private void loadOrderData() {
        Retrofit retrofit=createRetrofit();

        OrdaService ordaService=retrofit.create(OrdaService.class);

        Call<List<Orda>> call = ordaService.getOrders(userId);
        call.enqueue(new Callback<List<Orda>>() {
            @Override
            public void onResponse(Call<List<Orda>> call, Response<List<Orda>> response) {
                if (response.isSuccessful()) {
                    List<Orda> orders = response.body();
                    if (orders != null) {
                        orderList.clear();
                        orderList.addAll(orders);
                        orderAdapter.notifyDataSetChanged();
                    }
                } else {
                    // 处理请求失败的情况
                    Toast.makeText(OrderActivity.this,"订单获取失败",Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<List<Orda>> call, Throwable t) {
                // 处理网络请求失败的情况
                Toast.makeText(OrderActivity.this,"网络请求出错",Toast.LENGTH_LONG);
            }
        });

        // 刷新订单列表
        orderAdapter.notifyDataSetChanged();
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
