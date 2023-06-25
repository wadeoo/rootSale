package cn.edu.fzu.rootsale.adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


import cn.edu.fzu.rootsale.R;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Orda> cartItemList;
    private Context context;
    private int userId=-1;

    public CartAdapter(List<Orda> cartItemList, Context context) {
        this.cartItemList = cartItemList;
        this.context=context;

        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        userId=sharedPreferences.getInt("userId",-1);

    }

    public void setCartItemList(List<Orda> cartItemList) {
        this.cartItemList=cartItemList;
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView artworkImageView;
        private TextView artworkNameTextView;
        private TextView quantityTextView;
        private TextView totalPriceTextView;
        private TextView orderDateTextView;
        private Button deleteButton;

        public CartViewHolder(View itemView) {
            super(itemView);
            artworkImageView =itemView.findViewById(R.id.artworkImageView);
            artworkNameTextView = itemView.findViewById(R.id.artworkNameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(final Orda cartItem) {
            Picasso.get().load(cartItem.getArtworkImagePath()).into(artworkImageView);
            artworkNameTextView.setText(cartItem.getArtworkName());
            quantityTextView.setText("数量：" + cartItem.getQuantity());
            totalPriceTextView.setText("总价：" + cartItem.getTotalPrice());
            orderDateTextView.setText("下单日期：" + cartItem.getOrderDate());



        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Orda cartItem = cartItemList.get(position);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 处理删除按钮点击事件
                Retrofit retrofit = createRetrofit();

                OrdaService ordaService = retrofit.create(OrdaService.class);

                Call<Void> call = ordaService.deleteCartItemById(Math.toIntExact(cartItem.getId()));

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // 处理删除成功的情况，例如更新订单列表或显示提示信息
                            // 可以在此处刷新订单列表
                            loadCartData();
                        } else {
                            // 处理删除失败的情况，例如显示错误提示信息
                            // 可以在此处显示删除失败的提示信息
                            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // 处理网络请求失败的情况，例如显示错误提示信息
                        // 可以在此处显示网络请求失败的提示信息
                        Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        holder.bind(cartItem);
    }

    private void loadCartData() {
        // 创建 Retrofit 实例
        Retrofit retrofit = createRetrofit();

        // 创建 OrdaService 接口实例
        OrdaService ordaService = retrofit.create(OrdaService.class);

        // 调用后端接口获取订单列表
        Call<List<Orda>> call = ordaService.getCartItems(userId); // 假设您已经有一个 userId 变量用于指定用户ID
        call.enqueue(new Callback<List<Orda>>() {
            @Override
            public void onResponse(Call<List<Orda>> call, Response<List<Orda>> response) {
                if (response.isSuccessful()) {
                    // 成功获取到订单列表
                    List<Orda> cartItemList = response.body();
                    // 刷新订单列表
                    setData(cartItemList);
                } else {
                    // 请求失败，显示错误信息
                    Toast.makeText(context, "购物车数据加载失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Orda>> call, Throwable t) {
                // 请求失败，显示错误信息
                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 更新数据集
    public void setData(List<Orda> cartItemList) {
        this.cartItemList = cartItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
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
