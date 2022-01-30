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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestFragment extends Fragment {
    FirebaseAuth auth;
    DatabaseReference df;

    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private List<Request> requestList;

    private FloatingActionButton fa;

    private String userid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.request_fragment,container,false);

        auth = FirebaseAuth.getInstance();

        userid = auth.getCurrentUser().getUid();


        fa = view.findViewById(R.id.f_btn_addrequest);

        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frag,new AddRequestFragment()).commit();
            }
        });


        recyclerView = view.findViewById(R.id.rc_request);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestList = new ArrayList<>();

        df = FirebaseDatabase.getInstance().getReference("requestedskill");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    Request request = s.getValue(Request.class);

                    if(!request.getUserid().equals(userid)){
                        requestList.add(request);
                    }
                }

                RequestAdapter requestAdapter = new RequestAdapter(getActivity(),requestList,"request");

                recyclerView.setAdapter(requestAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
