package com.daffzzaqihaq.crudapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daffzzaqihaq.crudapi.Adapter.UserAdapter;
import com.daffzzaqihaq.crudapi.Api.ApiClient;
import com.daffzzaqihaq.crudapi.Api.ApiInterfaced;
import com.daffzzaqihaq.crudapi.Model.UserData;
import com.daffzzaqihaq.crudapi.Model.UserResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvUser)
    RecyclerView rvUser;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    // TODO 1 Membuat variable yg dibutuhkan
    // Membuat variable List
    private List<UserData>userDataList;
    private ApiInterfaced apiInterfaced;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Membuatobject list
        userDataList = new ArrayList<>();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        showProgress();

        // TODO 2 Mengambil data dari Server Rest API
        getData();
    }

    private void showProgress() {
        // Membuat object progress dailog untuk  kita gunakan
        progressDialog = new ProgressDialog(MainActivity.this);
        // Mengatur message
        progressDialog.setMessage("Loadong...");
        progressDialog.show();
    }

    private void getData() {
        // Membuat object Api User Interface
        apiInterfaced = ApiClient.getClient().create(ApiInterfaced.class);
        // Membuat obejct call
        Call<UserResponse> call = apiInterfaced.getUser(12);
        // Enqueue
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                // Respon sukses, hilangkan progress dialog
                progressDialog.dismiss();

                // Menghilangkan icon refresh
                swipeRefresh.setRefreshing(false);

                // Mengambil body object utk merespon
                UserResponse userResponse = response.body();
                // Mengabil json aray dgn nama data
                userDataList = userResponse.getData();

                // Mensetting layout recyclerview
                rvUser.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                // Memasukan context dan data ke adapter
                rvUser.setAdapter(new UserAdapter(MainActivity.this, userDataList));
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Respon sukses, hilangkan progress dialog
                progressDialog.dismiss();

                // Menghilangkan icon refresh
                swipeRefresh.setRefreshing(false);
                // Menampilakn error dari server
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
