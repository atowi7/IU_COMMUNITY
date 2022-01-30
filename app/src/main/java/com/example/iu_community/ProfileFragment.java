package com.example.iu_community;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    ImageView logoutimg,userimg,chatimg,editimg;
    TextView infotiltle,leveltitle,majortiltle,skillstilte,requeststitle,username,major,level;
    RecyclerView re_skills,re_requests;

    FirebaseAuth auth;

    String userid,suserid;

    DatabaseReference dfuser,dfskill,dfrequest;

    List<Skill> skillList;
    List<Request> requestList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.profile_fragment,container,false);

        auth = FirebaseAuth.getInstance();

        userid = auth.getCurrentUser().getUid();

        Bundle bundle = this.getArguments();

        if(bundle!=null){
            suserid=bundle.getString("userid");
        }

        logoutimg = view.findViewById(R.id.profile_logoutimg);

        logoutimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                getActivity().finish();
            }
        });

        editimg = view.findViewById(R.id.profile_editimg);

        editimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.frag,new EditProfileFragment()).commit();
            }
        });

        infotiltle = view.findViewById(R.id.profile_infotitle);
        majortiltle = view.findViewById(R.id.profile_majortitle);
        leveltitle= view.findViewById(R.id.profile_leveltitle);
        skillstilte = view.findViewById(R.id.profile_skillstitle);
        requeststitle = view.findViewById(R.id.profile_requestsstitle);


        userimg = view.findViewById(R.id.profile_userimg);

        chatimg = view.findViewById(R.id.profile_chat);

        username = view.findViewById(R.id.profile_username);
        major = view.findViewById(R.id.profile_major_txt);
        level = view.findViewById(R.id.profile_level_txt);

        re_skills = view.findViewById(R.id.profile_rec_skill);
        re_skills.setHasFixedSize(true);
        re_skills.setLayoutManager(new GridLayoutManager(getActivity(),3, RecyclerView.VERTICAL,false));

        skillList = new ArrayList<>();

        re_requests = view.findViewById(R.id.profile_rec_request);
        re_requests.setHasFixedSize(true);
        re_requests.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestList = new ArrayList<>();


        if(userid!=null&suserid!=null&&!userid.equals(suserid)){
            editimg.setVisibility(View.GONE);
            logoutimg.setVisibility(View.GONE);

            dfuser = FirebaseDatabase.getInstance().getReference().child("user").child(suserid);
            dfuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    infotiltle.setText("Info");
                    majortiltle.setText("Major");
                    leveltitle.setText("Level");

                    if (user.getUserimage().equals("default")) {
                        userimg.setImageResource(R.drawable.person_icon_foreground);
                    }else{
                        Picasso.get().load(user.getUserimage()).into(userimg);
                    }

                    chatimg.setImageResource(R.drawable.chat_icon);

                    username.setText(user.getUsername());
                    major.setText(user.getMajor());
                    level.setText(user.getLevel());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            chatimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open message page
                    Intent intent = new Intent(getActivity(), Message_p.class);
                    intent.putExtra("userid", suserid);
                    getActivity().startActivity(intent);
                }
            });

            dfskill = FirebaseDatabase.getInstance().getReference().child("skill");

            dfskill.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    skillList.clear();

                    skillstilte.setText("Skills");

                    for(DataSnapshot s : snapshot.getChildren()){
                        Skill skill = s.getValue(Skill.class);
                        if(skill.getUserid().equals(suserid)) {
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

            dfrequest = FirebaseDatabase.getInstance().getReference("requestedskill");
            dfrequest.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    requestList.clear();

                    requeststitle.setText("Requests");

                    for(DataSnapshot s : snapshot.getChildren()){
                        Request request = s.getValue(Request.class);

                        if(request.getUserid().equals(suserid)){
                            requestList.add(request);
                        }
                    }

                    RequestAdapter requestAdapter = new RequestAdapter(getActivity(),requestList,"owner");

                    re_requests.setAdapter(requestAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
            dfuser = FirebaseDatabase.getInstance().getReference().child("user").child(userid);

            dfuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    infotiltle.setText("Info");
                    majortiltle.setText("Major");
                    leveltitle.setText("Level");

                    if (user.getUserimage().equals("default")) {
                        userimg.setImageResource(R.drawable.person_icon_foreground);
                    } else {
                        Picasso.get().load(user.getUserimage()).into(userimg);
                    }

                    username.setText(user.getUsername());
                    major.setText(user.getMajor());
                    level.setText(user.getLevel());

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

                    skillstilte.setText("Skills");

                    for (DataSnapshot s : snapshot.getChildren()) {
                        Skill skill = s.getValue(Skill.class);
                        if (skill.getUserid().equals(userid)) {
                            skillList.add(skill);
                        }
                    }

                    SkillAdapter skillAdapter = new SkillAdapter(getActivity(), skillList, "owner");

                    re_skills.setAdapter(skillAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            dfrequest = FirebaseDatabase.getInstance().getReference("requestedskill");
            dfrequest.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    requestList.clear();

                    requeststitle.setText("Requests");

                    for(DataSnapshot s : snapshot.getChildren()){
                        Request request = s.getValue(Request.class);

                        if(request.getUserid().equals(userid)){
                            requestList.add(request);
                        }
                    }

                    RequestAdapter requestAdapter = new RequestAdapter(getActivity(),requestList,"profile");

                    re_requests.setAdapter(requestAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }



        return view;
    }


}
