package com.example.retrofit_pytania;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolder {
    @GET("db.json")
    public Call<List<Pytanie>> getPytania();
}
