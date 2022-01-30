package com.example.iu_community;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context c;
    List<User> userlist;


    public UserAdapter(Context c, List<User> userlist) {
        this.c = c;
        this.userlist = userlist;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.useritem, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userlist.get(position);

        if(user.getUserimage().equals("default")){
            holder.userimage.setImageResource(R.drawable.person_icon_foreground);
        }else{
            Picasso.get().load(user.getUserimage()).into(holder.userimage);
        }

        holder.userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open user profile
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userid",user.getUserid());
                profileFragment.setArguments(bundle);
                android.app.FragmentManager manager = ((Activity)c).getFragmentManager();
                manager.beginTransaction().replace(R.id.frag,profileFragment).commit();
            }
        });

        holder.username.setText(user.getUsername());

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open message page
                Intent intent = new Intent(c, Message_p.class);
                intent.putExtra("userid", user.getUserid());
                c.startActivity(intent);
            }
        });


    }

   //public void filter(String s){ }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView userimage;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.useritem_name);
            userimage = (ImageView) itemView.findViewById(R.id.useritem_image);

        }
    }
}
