package com.example.iu_community;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    FirebaseAuth auth;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        auth = FirebaseAuth.getInstance();

        userid = auth.getCurrentUser().getUid();

        getFragmentManager().beginTransaction().replace(R.id.frag,new ProfileFragment()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("userid",userid);
                        profileFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.frag,profileFragment).commit();
                        return true;
                    case R.id.doctor:
                        getFragmentManager().beginTransaction().replace(R.id.frag,new DoctorFragment()).commit();
                        return true;
                    case R.id.student:
                        getFragmentManager().beginTransaction().replace(R.id.frag,new LevelFragment()).commit();
                        return true;
                    case R.id.request:
                        getFragmentManager().beginTransaction().replace(R.id.frag,new RequestFragment()).commit();
                        return true;
                    case R.id.search:
                        getFragmentManager().beginTransaction().replace(R.id.frag,new SearchFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }
}
