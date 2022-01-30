package com.example.iu_community;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

public class StudentFragment extends Fragment {
    FirebaseAuth auth;
    DatabaseReference df;

    private ImageView backimg;
    private TextView leveltxt;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    private String userid;

    String level;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.student_fragment,container,false);

        auth = FirebaseAuth.getInstance();

        userid = auth.getCurrentUser().getUid();

        Bundle bundle = this.getArguments();

        if(bundle!=null){
            level=bundle.getString("level");
        }

        backimg = view.findViewById(R.id.student_backimg);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frag,new LevelFragment()).commit();
            }
        });

        leveltxt = view.findViewById(R.id.student_level_txt);
        leveltxt.setText("Level "+level);

        recyclerView = view.findViewById(R.id.rc_studentlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userList = new ArrayList<>();
        
        df = FirebaseDatabase.getInstance().getReference("user");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    User user = s.getValue(User.class);

                    if(!user.getUserid().equals(userid)&&user.getUsertype().equals("student")&&user.getLevel().equals(level)){
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
