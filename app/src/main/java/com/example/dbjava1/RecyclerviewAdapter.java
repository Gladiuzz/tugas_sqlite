package com.example.dbjava1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.UserViewHolder> {
    Context context;
    List<PersonBean> listPersonInfo;
    OnUserClickListener listener;



    public RecyclerviewAdapter(Context context, List<PersonBean> listPersonInfo, OnUserClickListener listener) {
        this.context = context;
        this.listPersonInfo = listPersonInfo;
        this.listener = listener;

    }

    public interface OnUserClickListener{
        void onUserClick(PersonBean currentPerson, String action);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item,parent,false);
        UserViewHolder userViewHolder = new UserViewHolder(view);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        final PersonBean currentPerson = listPersonInfo.get(position);
        holder.ctxtname.setText(currentPerson.getName());
        holder.ctxtage.setText(currentPerson.getAge()+"");
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClick(currentPerson, "Edit");
            }
        });

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClick(currentPerson,"Delete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPersonInfo.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView ctxtage, ctxtname;
        ImageView imgdelete, imgedit;
        public UserViewHolder(View view) {
            super(view);
            ctxtage=itemView.findViewById(R.id.ctxtAge);
            ctxtname=itemView.findViewById(R.id.ctxtName);
            imgdelete=itemView.findViewById(R.id.imgdelete);
            imgedit=itemView.findViewById(R.id.imgedit);
        }
    }
}
