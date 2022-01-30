package com.example.iu_community;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.SkillViewHolder> {

    Context c;
    List<Request> requestList;
    String t;


    public RequestAdapter(Context c, List<Request> requestList,String t) {
        this.c = c;
        this.requestList = requestList;
        this.t=t;
    }


    @NonNull
    @Override
    public RequestAdapter.SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.requestitem, parent, false);
        return new RequestAdapter.SkillViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Request request   = requestList.get(position);

        holder.title.setText(request.getTitle());

        if(t.equals("request")) {
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("userid", request.getUserid());
                    profileFragment.setArguments(bundle);
                    android.app.FragmentManager manager = ((Activity) c).getFragmentManager();
                    manager.beginTransaction().replace(R.id.frag, profileFragment).commit();
                }
            });
        }


    }



    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.requesttitle);

        }
    }
}
