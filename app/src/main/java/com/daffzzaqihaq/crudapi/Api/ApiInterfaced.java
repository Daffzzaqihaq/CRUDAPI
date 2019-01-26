package com.daffzzaqihaq.crudapi.Api;

import com.daffzzaqihaq.crudapi.Model.LoginBody;
import com.daffzzaqihaq.crudapi.Model.LoginResponse;
import com.daffzzaqihaq.crudapi.Model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterfaced {

    // Membuat login
    @POST("/api/login")
    Call<LoginResponse> postLogin(@Body LoginBody loginBody);

    // Membuat request Get data user
    @GET("/api/users")

    Call<UserResponse> getUser(
            @Query("per_page") int perPage
    );
}
