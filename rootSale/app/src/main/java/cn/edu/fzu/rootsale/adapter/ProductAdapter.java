package cn.edu.fzu.rootsale.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.edu.fzu.rootsale.R;
import cn.edu.fzu.rootsale.activity.LoginActivity;
import cn.edu.fzu.rootsale.activity.ProductActivity;
import cn.edu.fzu.rootsale.activity.ProductDetailActivity;
import cn.edu.fzu.rootsale.model.Artwork;

// 商品适配器
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Artwork> productList;
    private Context context;

    public ProductAdapter(List<Artwork> productList ,Context context) {
        this.productList = productList;
        this.context=context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artwork, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // 获取当前位置的商品数据
        Artwork artwork = productList.get(position);

        // 在 ViewHolder 中设置商品数据到对应的视图
        holder.bind(artwork);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 打开商品详细界面，并将所点击的商品对象传递过去
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("artwork", artwork);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProductList(List<Artwork> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    // 商品视图持有者
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView artworkImageView;
        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView priceTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            artworkImageView = itemView.findViewById(R.id.artworkImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);

        }

        public void bind(Artwork artwork) {
            // 使用 Picasso 加载图片
            // 设置商品数据到对应的视图
            Picasso.get().load(artwork.getImagePath()).placeholder(R.drawable.placeholder).into(artworkImageView);
            nameTextView.setText(artwork.getName());
            descriptionTextView.setText(artwork.getDescription());
            priceTextView.setText("价格: " + artwork.getPrice().toString() + " ¥");
        }

    }


}
