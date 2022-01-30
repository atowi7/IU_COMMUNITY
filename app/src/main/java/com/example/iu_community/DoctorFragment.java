package com.example.iu_community;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

public class DoctorFragment extends Fragment {
    FirebaseAuth auth;
    DatabaseReference df;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    private String userid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.doctor_fragment,container,false);

        recyclerView = view.findViewById(R.id.rc_doctorlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        auth = FirebaseAuth.getInstance();

        userid = auth.getCurrentUser().getUid();

        userList = new ArrayList<>();

        df = FirebaseDatabase.getInstance().getReference("user");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    User user = s.getValue(User.class);

                    if(!user.getUserid().equals(userid)&&user.getUsertype().equals("doctor")){
                        userList.add(user);
                    }
                }

                userAdapter = new UserAdapter(getActivity(),userList);

                recyclerView.setAdapter(userAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
