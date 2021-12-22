package com.hikki.masakapanih.networking;

import com.hikki.masakapanih.model.DetailResepModel;
import com.hikki.masakapanih.model.KategoriModel;
import com.hikki.masakapanih.model.ResepModel;
import com.hikki.masakapanih.model.SearchModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/categorys/recipes")
    Call<KategoriModel> getKategori();

    @GET("/api/recipes/{page}")
    Call<ResepModel> getResep(@Path("page") int page);

    @GET("/api/search")
    Call<SearchModel> getSearch(@Query("q") String key);

    @GET("/api/categorys/recipes/{key}/{page}")
    Call<ResepModel> getKategori(@Path("key") String key, @Path("page") int page);

    @GET("/api/recipe/{key}")
    Call<DetailResepModel> getDetailResep(@Path("key") String key);
}
