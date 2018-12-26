package com.example.yossi.storage;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AllUsersAdapter extends ArrayAdapter<User> {


    Context context;
    List<User> usersList;


    public AllUsersAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<User> usersList) {
        super(context, resource, textViewResourceId, usersList);

        this.context = context;
        this.usersList = usersList;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.image_listview_item_layout,parent,false);

        ImageView iv = view.findViewById(R.id.iv) ;


        User user = usersList.get(position);

        String url = user.profileImageUrl;

        Picasso.get().load(url).into(iv);


        return view;

    }
}
