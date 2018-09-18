package com.jdswarehouse.Retrofit;

import com.jdswarehouse.Response.DataResponse;
import com.jdswarehouse.Response.DispatchResponse;
import com.jdswarehouse.Response.ForgetResponse;
import com.jdswarehouse.Response.LoginResponse;
import com.jdswarehouse.Response.QuotationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dikhong on 02-07-2018.
 */

public interface JDSWarehouseService {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password,
                              @Field("device_token") String deviceToken,
                              @Field("device_type") String deviceType,
                              @Field("lat") String lat,
                              @Field("long") String lng);

    @FormUrlEncoded
    @POST("forgot_password")
    Call<ForgetResponse> forget(@Field("email") String email,
                                @Field("lat") String lat,
                                @Field("long") String lng);


    @GET("delivery_order_list")
    Call<DispatchResponse> dispatch(@Query("authorization") String authorization,
                                    @Query("lat") String lat,
                                    @Query("long") String lng);

    @FormUrlEncoded
    @POST("load_by")
    Call<DataResponse> assignMySelf(@Field("quotation_id") String quotationId,
                                    @Field("user_id") String userId,
                                    @Field("authorization") String authorization,
                                    @Field("lat") String lat,
                                    @Field("long") String lng);

    @FormUrlEncoded
    @POST("quotation_items")
    Call<QuotationResponse> quatationData(@Field("quotation_id") String quotationId,
                                          @Field("customer_id") String customerId,
                                          @Field("authorization") String authorization,
                                          @Field("lat") String lat,
                                          @Field("long") String lng);

    @FormUrlEncoded
    @POST("submit_order_status")
    Call<DataResponse> submitOrder(@Field("quotation_id") String quotationId,
                                   @Field("order_status") String orderStatus,
                                   @Field("authorization") String authorization,
                                   @Field("lat") String lat,
                                   @Field("long") String lng);

    @FormUrlEncoded
    @POST("scan_qrcode")
    Call<DataResponse> scanCode(@Field("product_id") String productId,
                                @Field("qr_code") String qrCode,
                                @Field("quote_item_id") String quoteItemId,
                                @Field("authorization") String authorization,
                                @Field("lat") String lat,
                                @Field("long") String lng);

    @FormUrlEncoded
    @POST("submit_quantity_status")
    Call<DataResponse> quantityStatus(@Field("quotes_item_id") String quotes_item_id,
                                      @Field("quantity") String quantity,
                                      @Field("status") String status,
                                      @Field("notes") String notes,
                                      @Field("authorization") String authorization,
                                      @Field("lat") String lat,
                                      @Field("long") String lng);


}
