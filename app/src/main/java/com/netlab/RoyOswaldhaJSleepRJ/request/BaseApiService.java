package com.netlab.RoyOswaldhaJSleepRJ.request;

import com.netlab.RoyOswaldhaJSleepRJ.model.*;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface BaseApiService {
    //Account API
    @GET("account/{id}/getAccount")
    Call<Account> getAccount(@Path("id") int id);

    @POST("account/login")
    Call<Account> requestLogin(@Query("email") String email,
                               @Query("password") String password);

    @POST("account/register")
    Call<Account> requestRegister(@Query("name") String username,
                                  @Query("email") String email,
                                  @Query("password") String password);

    @POST("account/{id}/registerRenter")
    Call<Renter> requestRenter(@Path("id") int id,
                               @Query("username") String username,
                               @Query("address") String address,
                               @Query("phoneNumber") String phoneNumber);


    @POST("account/{id}/topUp")
    Call<Boolean> requestTopUp(@Path("id") int id,
                               @Query("balance") double balance);

    @POST("account/{id}/withdraw")
    Call<Boolean>requestWithdraw(@Path("id") int id,
                                 @Query("balance") double balance);


    @POST("account/{id}/addToFavorite")
    Call<Boolean> addToFavorite(@Path("id") int id,
                                @Query("roomId") int roomId);

    @GET("account/getRenterOfRoom")
    Call<Renter>getRenterOfRoom(@Query("accountId") int accountId);

    @PUT("account/{id}/removeFromFavorite")
    Call<Boolean>removeFromFavorite(@Path("id") int id, @Query("roomId") int roomId);

    @GET("account/{id}/favoriteRoomCheck")
    Call<Boolean>favoriteRoomCheck(@Path("id") int accountId, @Query("roomId") int roomId);

    //Renter API
    @GET("renter/{id}")
    Call<Renter> getRenter (@Path("id") int id);

    //Room API
    @GET("room/{id}/")
    Call<Room> getRoom (@Path("id") int id);


    @POST("room/create")
    Call<Room> requestRoom(@Query("accountId") int accountId,
                           @Query("name") String name,
                           @Query("size") int size,
                           @Query("price") int price,
                           @Query("facility") ArrayList<Facility> facility,
                           @Query("city") City city,
                           @Query("address") String address,
                           @Query("bedType") BedType bedType,
                           @Query("image") String image);

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom (@Query("page") int page,
                                 @Query("pageSize") int pageSize);


    @POST("room/{id}/rating")
    Call<Boolean> roomRating(@Path("id") int id,
                             @Query("rating")int rating);

    @GET("room/getFavoriteRoom")
    Call<List<Room>> getFavoriteRoom(@Query("accountId") int accountId);

    @GET("room/roomByRenter")
    Call<List<Room>> getRoomByRenter(@Query("renterId") int renterId);


    @GET("room/roomByCity")
    Call<List<Room>>getRoomByCity(@Query("city") ArrayList<City> city);

    //Payment API

    @GET("price/{id}")
    Call<Price> getPrice(@Path("id") int id);

    @POST("payment/create")
    Call<Payment> requestPayment(@Query("buyerId") int buyerId,
                                 @Query("renterId") int renterId,
                                 @Query("roomId") int roomId,
                                 @Query("from") String from,
                                 @Query("to") String to);

    @GET("payment/getPaymentWaitingRenter")
    Call<List<Payment>> getPaymentWaitingRenter(@Query("renterId") int renterId);

    @GET("payment/getPaymentHistoryRenter")
    Call<List<Payment>> getPaymentHistoryRenter(@Query("renterId") int renterId);

    @GET("payment/getPaymentWaitingBuyer")
    Call<List<Payment>> getPaymentWaitingBuyer(@Query("buyerId") int buyerId);

    @GET("payment/getPaymentHistoryBuyer")
    Call<List<Payment>> getPaymentHistoryBuyer(@Query("buyerId") int buyerId);

    @POST("payment/{id}/accept")
    Call<Boolean> acceptPayment(@Path("id") int id,
                                @Query("buyerId") int buyerId,
                                @Query("renterId") int renterId);

    @PUT("payment/{id}/cancel")
    Call<Boolean> cancelPayment(@Path("id") int id);


    @POST("payment/{id}/useVoucher")
    Call<Payment>useVoucher(@Path("id")int id,
                            @Query("code") int code);

    @GET("payment/{id}/getApply")
    Call<List<Voucher>>getApply(@Path("id")int id);

    //Voucher API
    @POST("voucher/create")
    Call<Voucher> createVoucher(@Query("name") String name,
                                @Query("code") int code,
                                @Query("type") Type type,
                                @Query("minimum") double Minimum,
                                @Query("cut") double cut);

    @POST("voucher/create")
    Call<Voucher> requestVoucher(@Query("name") String name,
                                 @Query("code") int code,
                                 @Query("type") Type type,
                                 @Query("minimum") double minimum,
                                 @Query("cut") double cut);

    @GET("voucher/getAvailable")
    Call<List<Voucher>> getAvailableVoucher();
}
