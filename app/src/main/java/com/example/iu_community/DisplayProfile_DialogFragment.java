package com.example.iu_community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DisplayProfile_DialogFragment extends DialogFragment {
    ImageView userimg;
    TextView username,usermajor,userlevel;

    RecyclerView re_skills;

    String userid;

    DatabaseReference dfuser,dfskill;

    List<Skill> skillList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_displayprofile,container,false);
        userimg = v.findViewById(R.id.displayprofile_userimg);

        username = v.findViewById(R.id.displayprofile_username);
        usermajor = v.findViewById(R.id.displayprofile_major);
        userlevel = v.findViewById(R.id.displayprofile_level);

        re_skills = v.findViewById(R.id.displayprofile_skill_rc);
        re_skills.setHasFixedSize(true);
        re_skills.setLayoutManager(new LinearLayoutManager(getActivity()));

        skillList = new ArrayList<>();

        dfuser = FirebaseDatabase.getInstance().getReference().child("user").child(getUserid());

        dfuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user.getUserimage().equals("default")) {
                    userimg.setImageResource(R.drawable.person_icon_foreground);
                }else{
                    Picasso.get().load(user.getUserimage()).into(userimg);
                }

                username.setText(user.getUsername());

                usermajor.setText(user.getMajor());

                userlevel.setText("Level : "+user.getLevel());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dfskill = FirebaseDatabase.getInstance().getReference().child("skill");

        dfskill.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                skillList.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    Skill skill = s.getValue(Skill.class);
                    if(skill.getUserid().equals(getUserid())) {
                        skillList.add(skill);
                    }
                }

                SkillAdapter skillAdapter = new SkillAdapter(getActivity(),skillList,"other");

                re_skills.setAdapter(skillAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
