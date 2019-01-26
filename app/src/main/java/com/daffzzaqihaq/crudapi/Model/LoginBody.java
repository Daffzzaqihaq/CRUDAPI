package com.daffzzaqihaq.crudapi.Model;

import com.google.gson.annotations.SerializedName;

public class LoginBody {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
