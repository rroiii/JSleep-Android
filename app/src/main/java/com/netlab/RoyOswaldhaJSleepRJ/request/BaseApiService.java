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

}
