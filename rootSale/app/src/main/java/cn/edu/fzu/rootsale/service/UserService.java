package cn.edu.fzu.rootsale.service;

import cn.edu.fzu.rootsale.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @GET("/login")
    Call<Integer> login(@Query("username") String username, @Query("password") String password);


    @POST("/register")
    Call<Void> register(@Body User user);

    @GET("/user/{id}")
    Call<User> getUser(@Path("id") int id);

    @PATCH("/user/profileChange/{id}")
    Call<Void> changeProfile(@Body User user,@Path("id") int id);
}

