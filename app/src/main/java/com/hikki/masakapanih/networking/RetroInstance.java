package com.hikki.masakapanih.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {
    private Retrofit retroinstance;

    public Retrofit getRetro(){

        if(retroinstance == null){
            retroinstance = new Retrofit.Builder().baseUrl("https://masak-apa-tomorisakura.vercel.app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retroinstance;
    }
}
