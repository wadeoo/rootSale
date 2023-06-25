package cn.edu.fzu.rootsale.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.fzu.rootsale.R;
import cn.edu.fzu.rootsale.constant.Constant;
import cn.edu.fzu.rootsale.model.Artwork;
import cn.edu.fzu.rootsale.model.Orda;
import cn.edu.fzu.rootsale.service.OrdaService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailActivity extends AppCompatActivity {

    private Artwork mArtwork;
    private EditText quantityEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        // 隐藏状态栏（通知栏）
        getSupportActionBar().hide();

        // 获取传递过来的商品对象
        mArtwork = (Artwork) getIntent().getSerializableExtra("artwork");

        if(mArtwork!=null){
            setUpUI();
        }

    }

    private void setUpUI() {
        // 显示商品详细信息
        TextView nameTextView = findViewById(R.id.textViewArtworkName);
        TextView priceTextView = findViewById(R.id.textViewArtworkPrice);
        TextView descriptionTextView = findViewById(R.id.textViewArtworkDescription);
        TextView detailTextView = findViewById(R.id.textViewArtworkDetail);
        ImageView imageView = findViewById(R.id.imageViewArtwork);
        quantityEditView=findViewById(R.id.editTextQuantity);
        FloatingActionButton addCartFab=findViewById(R.id.addCartFab);

        nameTextView.setText(mArtwork.getName());
        priceTextView.setText(String.valueOf(mArtwork.getPrice())+" ¥");
        descriptionTextView.setText(mArtwork.getDescription());
        detailTextView.setText(mArtwork.getDetails());
        addCartFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(v);
            }
        });

        Picasso.get().load(mArtwork.getImagePath()).placeholder(R.drawable.placeholder).into(imageView);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_cart:
                        // 处理点击购物车的逻辑
                        Intent intent1 = new Intent(ProductDetailActivity.this, CartActivity.class);
                        startActivity(intent1);
                        Toast.makeText(ProductDetailActivity.this, "购物车", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_products:
                        // 处理点击商品浏览的逻辑
                        Intent intent2 = new Intent(ProductDetailActivity.this, ProductActivity.class);
                        startActivity(intent2);
                        Toast.makeText(ProductDetailActivity.this, "商品", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_orders:
                        // 处理点击订单查看的逻辑
                        Intent intent3 = new Intent(ProductDetailActivity.this, OrderActivity.class);
                        startActivity(intent3);
                        Toast.makeText(ProductDetailActivity.this, "订单", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_profile:
                        // 处理点击个人信息的逻辑
                        Intent intent4 = new Intent(ProductDetailActivity.this, ProfileActivity.class);
                        startActivity(intent4);
                        Toast.makeText(ProductDetailActivity.this, "个人信息", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
    }


    // 在此方法中实现加入购物车操作
    private void addToCart(View v) {
        // 处理加入购物车的逻辑
        // 将菜品添加到购物车中

        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId=sharedPreferences.getInt("userId",-1);

        int quantity=0;
        if(quantityEditView.getText().toString().isEmpty()){
            quantity=1; // 设置购买数量
        }else{
            quantity=(Integer.parseInt(quantityEditView.getText().toString()));
        }

        //填充newOrda对象
        Orda newOrda = new Orda();
        newOrda.setUserId((long) userId);
        newOrda.setQuantity(quantity);
        newOrda.setArtworkName(mArtwork.getName());
        newOrda.setArtworkImagePath(mArtwork.getImagePath());
        newOrda.setTotalPrice(mArtwork.getPrice().multiply(BigDecimal.valueOf(quantity)));
        newOrda.setIsCart(1);

        // 获取当前日期
        Date currentDate = new Date();

        // 创建日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 格式化日期为字符串
        String formattedDate = dateFormat.format(currentDate);

        // 设置订单日期
        newOrda.setOrderDate(formattedDate);


        Retrofit retrofit = createRetrofit();

        OrdaService ordaService = retrofit.create(OrdaService.class);

        Call<Void> call = ordaService.addToOrderOrCart(newOrda);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // 处理响应
                if (response.isSuccessful()) {
                    // 添加成功
                    Toast.makeText(ProductDetailActivity.this, "已加入购物车", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // 添加失败
                    Toast.makeText(ProductDetailActivity.this, "加入购物车失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // 处理请求失败的情况
                Toast.makeText(ProductDetailActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
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
