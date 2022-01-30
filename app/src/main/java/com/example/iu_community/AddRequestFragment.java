package com.example.iu_community;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.UUID;

public class AddRequestFragment extends Fragment {
    private ImageView backimg;

    FirebaseAuth auth;
    DatabaseReference df;
    private String userid;

    EditText requesttitle_ed;
    private Button sendbtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.addrequest_fragment,container,false);

        backimg = view.findViewById(R.id.addrequest_backimg);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frag,new RequestFragment()).commit();
            }
        });

        auth = FirebaseAuth.getInstance();

        userid = auth.getCurrentUser().getUid();


        String requestid = UUID.randomUUID().toString();

        requesttitle_ed = view.findViewById(R.id.addrequest_requesttitle_ed);

        sendbtn = view.findViewById(R.id.addrequest_senditem);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requesttitle_ed.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Filled is empty", Toast.LENGTH_SHORT).show();
                }else{
                    df = FirebaseDatabase.getInstance().getReference();
                    Request request = new Request(requestid,requesttitle_ed.getText().toString(),userid);
                    df.child("requestedskill").push().setValue(request);

                    Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
                }

                requesttitle_ed.setText("");
            }
        });

        return view;
    }
}
