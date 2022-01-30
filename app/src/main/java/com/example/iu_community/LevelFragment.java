package com.example.iu_community;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class LevelFragment extends Fragment {
    Button l1,l2,l3,l4,l5,l6,l7,l8,l9,l10;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.level_fragment,container,false);

        l1 = view.findViewById(R.id.level1_btn);
        l2 = view.findViewById(R.id.level2_btn);
        l3 = view.findViewById(R.id.level3_btn);
        l4 = view.findViewById(R.id.level4_btn);
        l5 = view.findViewById(R.id.level5_btn);
        l6 = view.findViewById(R.id.level6_btn);
        l7 = view.findViewById(R.id.level7_btn);
        l8 = view.findViewById(R.id.level8_btn);
        l9 = view.findViewById(R.id.level9_btn);
        l10 = view.findViewById(R.id.level10_btn);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("level",l1.getText().toString());
                studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("level",l2.getText().toString());
                studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("level",l3.getText().toString());
                studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("level",l4.getText().toString());
                studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("level",l5.getText().toString());
                studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("level",l6.getText().toString());
                studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        l7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
               Bundle bundle = new Bundle();
               bundle.putString("level",l7.getText().toString());
               studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        l8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("level",l8.getText().toString());
                studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        l9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("level",l9.getText().toString());
                studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        l10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentFragment studentFragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("level",l10.getText().toString());
                studentFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frag,studentFragment).commit();
            }
        });

        return view;
    }
}
