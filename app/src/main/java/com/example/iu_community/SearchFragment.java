package com.example.iu_community;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    FirebaseAuth auth;
    DatabaseReference dfuser,dfskill;

    private EditText searched;
    private ImageView searchbtn_img;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    private String userid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.search_fragment,container,false);

        searched = view.findViewById(R.id.search_bar);
        searchbtn_img = view.findViewById(R.id.searchbtn_img);

        recyclerView = view.findViewById(R.id.rc_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        auth = FirebaseAuth.getInstance();

        userid = auth.getCurrentUser().getUid();

        userList = new ArrayList<>();

        searchbtn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(searched.getText().toString());
            }
        });

        return view;
    }
    public void search(String skilltitle){
        if(skilltitle.isEmpty()){
            Toast.makeText(getActivity(), "Filled is empty", Toast.LENGTH_SHORT).show();
        }else {
            dfskill = FirebaseDatabase.getInstance().getReference("skill");
            dfskill.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        Skill skill = s.getValue(Skill.class);
                        if (skill.getTitle().toLowerCase().contains(skilltitle.toLowerCase())) {
                            dfuser = FirebaseDatabase.getInstance().getReference().child("user").child(skill.getUserid());
                            dfuser.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    userList.clear();

                                    User user = snapshot.getValue(User.class);
                                    userList.add(user);

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else {
                            //Toast.makeText(getActivity(), "no results", Toast.LENGTH_SHORT).show();
                        }
                    }

                    userAdapter = new UserAdapter(getActivity(), userList);

                    recyclerView.setAdapter(userAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
}
