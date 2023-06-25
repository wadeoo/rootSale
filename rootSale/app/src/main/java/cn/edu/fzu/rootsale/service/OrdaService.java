package cn.edu.fzu.rootsale.service;
import java.util.List;

import cn.edu.fzu.rootsale.model.Orda;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrdaService {
    @POST("/order/addToOrderOrCart")
    Call<Void> addToOrderOrCart(@Body Orda orda);

    @GET("/order/getOrders")
    Call<List<Orda>> getOrders(@Query("userId") int userId);

    @GET("/order/getCartItems")
    Call<List<Orda>> getCartItems(@Query("userId") int userId);


    @DELETE("/order/{id}")
    Call<Void> deleteOrderById(@Path("id") int orderId);

    @DELETE("/order/cart/{id}")
    Call<Void> deleteCartItemById(@Path("id") int cartItemId);

    @POST("/order/cart/checkOut")
    Call<Void> checkOut(@Query("userId") int userId);
}
