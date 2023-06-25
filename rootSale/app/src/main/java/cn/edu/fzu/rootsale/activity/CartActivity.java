package cn.edu.fzu.rootsale.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fzu.rootsale.R;
import cn.edu.fzu.rootsale.adapter.CartAdapter;
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

public class CartActivity extends AppCompatActivity {
    private List<Orda> cartItemList;
    private RecyclerView cartRecyclerView;
    private FloatingActionButton checkOutFab;
    private CartAdapter cartAdapter;
    private int userId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs",MODE_PRIVATE);
        userId=sharedPreferences.getInt("userId",-1);

        // 隐藏标题栏
        getSupportActionBar().hide();

        // 初始化购物车列表
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkOutFab=findViewById(R.id.checkOutFab);
        cartItemList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartItemList, CartActivity.this);
        cartRecyclerView.setAdapter(cartAdapter);

        // 添加购物车项
        addCartItems();

        checkOutFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItemList.size()>0){
                    checkout();
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem(R.id.menu_cart).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_cart:
                        // 处理点击购物车的逻辑
                        Toast.makeText(CartActivity.this, "购物车", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_products:
                        // 处理点击商品浏览的逻辑
                        Intent intent1 = new Intent(CartActivity.this, ProductActivity.class);
                        startActivity(intent1);
                        Toast.makeText(CartActivity.this, "商品", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_orders:
                        // 处理点击订单查看的逻辑
                        Intent intent2 = new Intent(CartActivity.this, OrderActivity.class);
                        startActivity(intent2);
                        Toast.makeText(CartActivity.this, "订单", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_profile:
                        // 处理点击个人信息的逻辑
                        Intent intent3 = new Intent(CartActivity.this, ProfileActivity.class);
                        startActivity(intent3);
                        Toast.makeText(CartActivity.this, "个人信息", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

    }

    private void checkout(){
        // 创建 Retrofit 实例
        Retrofit retrofit = createRetrofit();

        // 创建 DishService 接口实例
        OrdaService ordaService = retrofit.create(OrdaService.class);

        // 发起获取菜品列表的请求
        Call<Void> call = ordaService.checkOut(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CartActivity.this , "结算成功", Toast.LENGTH_LONG).show();
                    addCartItems();
                } else {
                    Toast.makeText(CartActivity.this , "结算失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CartActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addCartItems() {
        // 创建 Retrofit 实例
        Retrofit retrofit = createRetrofit();

        // 创建 DishService 接口实例
        OrdaService ordaService = retrofit.create(OrdaService.class);

        // 发起获取菜品列表的请求
        Call<List<Orda>> call = ordaService.getCartItems(userId);
        call.enqueue(new Callback<List<Orda>>() {
            @Override
            public void onResponse(Call<List<Orda>> call, Response<List<Orda>> response) {
                if (response.isSuccessful()) {
                    cartItemList = response.body();
                    cartAdapter.setCartItemList(cartItemList);
                    cartAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(CartActivity.this , "购物车数据获取失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Orda>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
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
