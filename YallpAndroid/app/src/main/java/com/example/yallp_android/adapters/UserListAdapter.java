package com.example.yallp_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.UserListElement;

import java.util.ArrayList;

public class UserListAdapter  extends RecyclerView.Adapter<com.example.yallp_android.adapters.UserListAdapter.MyViewHolder> {

        public interface UserListAdapterClickListener {

            void userListAdapterClick(int id);
        }

        private Context context;
        private ArrayList<UserListElement> users;
        private LayoutInflater inflater;
        private com.example.yallp_android.adapters.UserListAdapter.UserListAdapterClickListener userListAdapterClickListener;

        public UserListAdapter(Context context, ArrayList<UserListElement> users, com.example.yallp_android.adapters.UserListAdapter.UserListAdapterClickListener userListAdapterClickListener) {
            inflater = LayoutInflater.from(context);
            this.context = context;
            this.users = users;
            this.userListAdapterClickListener = userListAdapterClickListener;
        }

        @Override
        public com.example.yallp_android.adapters.UserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.user_profile_list, parent, false);
            return new com.example.yallp_android.adapters.UserListAdapter.MyViewHolder(context, view, userListAdapterClickListener);
        }

        @Override
        public void onBindViewHolder(com.example.yallp_android.adapters.UserListAdapter.MyViewHolder holder, int position) {
            holder.setData(users.get(position), position);
        }


        @Override
        public int getItemCount() {
            return users.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            Context context;
            TextView username;
            int id = -1 ;

            int position;
            UserListElement userListElement;
            UserListAdapterClickListener userListAdapterClickListener;

            MyViewHolder(Context context, View view, com.example.yallp_android.adapters.UserListAdapter.UserListAdapterClickListener userListAdapterClickListener) {
                super(view);
                this.context = context;
                username = view.findViewById(R.id.userNameText);
                this.userListAdapterClickListener = userListAdapterClickListener;
                view.setOnClickListener(this);
            }

            void setData(UserListElement userListElement, int position) {
                this.userListElement = userListElement;
                this.position = position;
                username.setText(userListElement.getUsername());
                id = userListElement.getId();

            }

            @Override
            public void onClick(View view) {
                userListAdapterClickListener.userListAdapterClick (id);
            }
        }
    }

