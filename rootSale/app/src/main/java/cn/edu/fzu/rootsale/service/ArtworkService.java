package cn.edu.fzu.rootsale.service;

import java.util.List;

import cn.edu.fzu.rootsale.model.Artwork;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ArtworkService {
    @GET("/artwork")
    Call<List<Artwork>> getArtworkList();
}
