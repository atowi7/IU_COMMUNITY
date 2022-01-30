package com.example.iu_community;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {

    Context c;
    List<Skill> skilllist;
    String t;


    public SkillAdapter(Context c, List<Skill> skilllist,String t) {
        this.c = c;
        this.skilllist = skilllist;
        this.t=t;
    }


    @NonNull
    @Override
    public SkillAdapter.SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.skill, parent, false);
        return new SkillAdapter.SkillViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Skill skill = skilllist.get(position);

        holder.title.setText(skill.getTitle());
        holder.rate.setText(skill.getRate()+"/10");
        holder.attachimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DisplaySkillattach_DialogFragment displaySkillattach_dialogFragment = new DisplaySkillattach_DialogFragment();
                displaySkillattach_dialogFragment.setImgurl(skill.getAttachimgurl());
                androidx.fragment.app.FragmentManager fragmentManager = ((AppCompatActivity)c).getSupportFragmentManager();
                displaySkillattach_dialogFragment.show(fragmentManager,"skillimg_fragment");
            }
        });

        if(t.equals("owner")){
            holder.deleteimg.setVisibility(View.VISIBLE);
        }else if(t.equals("other")){
            holder.deleteimg.setVisibility(View.GONE);
        }

        holder.deleteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Are you sure to delete the skill?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference dfskill = FirebaseDatabase.getInstance().getReference().child("skill").child(skill.getId());
                        dfskill.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                snapshot.getRef().removeValue();

                                Toast.makeText(c, "Deleted is success", Toast.LENGTH_SHORT).show();

                                FragmentManager manager = ((Activity)c).getFragmentManager();

                                manager.beginTransaction().replace(R.id.frag,new ProfileFragment()).commit();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


    }



    @Override
    public int getItemCount() {
        return skilllist.size();
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView rate;
        public ImageView attachimg;
        public ImageView deleteimg;


        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.skilltitle_tct);
            rate = (TextView) itemView.findViewById(R.id.skillrate_txt);
            attachimg = (ImageView) itemView.findViewById(R.id.attachimg);
            deleteimg = (ImageView) itemView.findViewById(R.id.deleteimg);

        }
    }
}
