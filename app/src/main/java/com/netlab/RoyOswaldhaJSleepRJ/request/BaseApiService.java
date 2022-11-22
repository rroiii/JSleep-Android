package com.netlab.RoyOswaldhaJSleepRJ.request;

import com.netlab.RoyOswaldhaJSleepRJ.model.*;

import retrofit2.Call;
import retrofit2.http.*;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);

    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int id);

    @GET("renter/{id}")
    Call<Renter> getRenter (@Path("id") int id);

    @GET("price/{id}")
    Call<Price> getPrice(@Path("id") int id);

    @POST("account/login")
    Call<Account> requestLogin(@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> requestRegister(@Query("name") String username, @Query("email") String email, @Query("password") String password);
}
