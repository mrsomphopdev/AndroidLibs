package com.atbot43.androidlib;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mrdevxus on 1/12/18.
 */

public interface ApiService {

    @GET("customer/{token}")
    Call<CustomerDao> getCustomer(@Path("token") String token);
}
