package com.netlab.RoyOswaldhaJSleepRJ.request;

import com.netlab.RoyOswaldhaJSleepRJ.model.*;

import java.util.ArrayList;
import java.util.List;

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

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page, @Query("pageSize") int pageSize);

    @POST("account/login")
    Call<Account> requestLogin(@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> requestRegister(@Query("name") String username, @Query("email") String email, @Query("password") String password);

    @POST("account/{id}/registerRenter")
    Call<Account> requestRenter(@Path("id") int id, @Query("username") String username, @Query("address") String address, @Query("phoneNumber") String phoneNumber);

    @POST("room/create")
    Call<Room> requestRoom(@Query("accountId") int accountId, @Query("name") String name, @Query("size") int size, @Query("price") int price, @Query("facility") ArrayList<Facility> facility, @Query("city") City city, @Query("address") String address, @Query("bedType") BedType bedType);
}
