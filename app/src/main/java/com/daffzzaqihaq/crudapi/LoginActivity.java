package com.daffzzaqihaq.crudapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daffzzaqihaq.crudapi.Api.ApiClient;
import com.daffzzaqihaq.crudapi.Api.ApiInterfaced;
import com.daffzzaqihaq.crudapi.Model.LoginBody;
import com.daffzzaqihaq.crudapi.Model.LoginResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    // TODO 1 Membuat variable yg dibutuhkan
    // Membuat variable untuk animasi loading menggunakan progress dialog
    private ProgressDialog progressDialog;
    private LoginBody loginBody;
    private ApiInterfaced apiInterfaced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        showProgress();
        getData();
        login();
    }

    private void login() {
        // Membuat object Api Interface
        apiInterfaced = ApiClient.getClient().create(ApiInterfaced.class);
        Call<LoginResponse> call = apiInterfaced.postLogin(loginBody);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();

                if (response.body().getError() !=null && response.body().getToken() !=null) {
                    // Menampilkan response api berupa token ke dalam toast
                    Toast.makeText(LoginActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                }


                // Menampilkan response message
                Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                // Berpindah halaman ke MainActivity
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getData() {
        // Buat object LoginBody
        loginBody = new LoginBody();
        // Mengisi LoginBody
        loginBody.setEmail(edtEmail.getText().toString());
        loginBody.setPassword(edtPassword.getText().toString());
    }

    private void showProgress() {
        // Membuat object progress dialog
        progressDialog = new ProgressDialog(LoginActivity.this);
        // Menambahkan message pada loading
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Harap Tunggu");
        progressDialog.setMessage("Loading ...");
        // Menampilkan progress dialog
        progressDialog.show();
    }
}
