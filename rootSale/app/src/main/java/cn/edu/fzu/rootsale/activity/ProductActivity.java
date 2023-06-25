package cn.edu.fzu.rootsale.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fzu.rootsale.R;
import cn.edu.fzu.rootsale.adapter.ProductAdapter;
import cn.edu.fzu.rootsale.constant.Constant;
import cn.edu.fzu.rootsale.model.Artwork;
import cn.edu.fzu.rootsale.service.ArtworkService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Artwork> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getSupportActionBar().hide();

        // 初始化商品列表数据，这里假设有一个名为 productList 的 List<Artwork> 数据
        getProductList();

        productList = new ArrayList<>();

        // 获取 RecyclerView 实例
        recyclerView = findViewById(R.id.productRecyclerView);

        // 创建和设置布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // 创建适配器并设置给 RecyclerView
        productAdapter = new ProductAdapter(productList, ProductActivity.this);
        recyclerView.setAdapter(productAdapter);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem(R.id.menu_products).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_cart:
                        // 处理点击购物车的逻辑
                        Intent intent1 = new Intent(ProductActivity.this, CartActivity.class);
                        startActivity(intent1);
                        Toast.makeText(ProductActivity.this, "购物车", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_products:
                        // 处理点击商品浏览的逻辑
                        Toast.makeText(ProductActivity.this, "商品", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_orders:
                        // 处理点击订单查看的逻辑
                        Intent intent2 = new Intent(ProductActivity.this, OrderActivity.class);
                        startActivity(intent2);
                        Toast.makeText(ProductActivity.this, "订单", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_profile:
                        // 处理点击个人信息的逻辑
                        Intent intent3 = new Intent(ProductActivity.this, ProfileActivity.class);
                        startActivity(intent3);
                        Toast.makeText(ProductActivity.this, "个人信息", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });


    }


    private void getProductList() {
        // 在此处根据需要从数据库或其他数据源获取商品列表数据

        // 创建 Retrofit 实例
        Retrofit retrofit = createRetrofit();

        // 创建 接口实例
        ArtworkService artworkService = retrofit.create(ArtworkService.class);

        // 发起获取菜品列表的请求
        Call<List<Artwork>> call = artworkService.getArtworkList();
        call.enqueue(new Callback<List<Artwork>>() {
            @Override
            public void onResponse(Call<List<Artwork>> call, Response<List<Artwork>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    productAdapter.setProductList(productList);
                    productAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ProductActivity.this, "商品数据获取失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Artwork>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
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