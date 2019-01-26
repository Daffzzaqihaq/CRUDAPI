package com.daffzzaqihaq.crudapi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daffzzaqihaq.crudapi.Model.UserData;
import com.daffzzaqihaq.crudapi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    // Membuat variable untuk menampung data yg dibutuhkan adapter
    private final Context context;
    private final List<UserData> userDataList;


    public UserAdapter(Context context, List<UserData> userDataList) {
        this.context = context;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Menggunakan xml yg diinginkan
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Mengambil data list
        UserData userData = userDataList.get(i);

        // Menampilkan ke layar
        viewHolder.txtFirstName.setText(userData.getFirstName());
        viewHolder.txtLastName.setText(userData.getLastName());

        // Membuat object requestoption
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken);

        Glide.with(context).load(userData.getAvatar()).apply(options).into(viewHolder.imgAvatar);

    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        ImageView imgAvatar;
        @BindView(R.id.txtFirstName)
        TextView txtFirstName;
        @BindView(R.id.txtLastName)
        TextView txtLastName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
