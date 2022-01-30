package com.example.iu_community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText usernameed,emailed,passed,cpassed;

    RadioGroup radioGroup;

    RadioButton radioButton;

    TextView login;

    FirebaseAuth auth;

    DatabaseReference dfuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        usernameed = findViewById(R.id.username_ed);
        emailed = findViewById(R.id.email_ed);
        passed = findViewById(R.id.pass_ed);
        cpassed = findViewById(R.id.cpass_ed);

        radioGroup = findViewById(R.id.rg_usertype);

        login = findViewById(R.id.l_txt);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });

        auth = FirebaseAuth.getInstance();

    }

    public void signup(View v){
        if(usernameed.getText().toString().isEmpty()||emailed.getText().toString().isEmpty()||passed.getText().toString().isEmpty()||cpassed.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter a valid data", Toast.LENGTH_SHORT).show();
        }else {
            if(cpassed.getText().toString().equals(passed.getText().toString())){
                String name = usernameed.getText().toString();
                String email = emailed.getText().toString();
                String pass = passed.getText().toString();

                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

                String usertype=radioButton.getText().toString();;



                if(!usertype.isEmpty()){
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Signup.this, "Registration is successful", Toast.LENGTH_SHORT).show();

                                FirebaseUser firebaseUser = auth.getCurrentUser();

                                DatabaseReference dfuser = FirebaseDatabase.getInstance().getReference().child("user");
                                User user = new User(firebaseUser.getUid(),name,email,pass,"default",usertype,"Null","0");
                                dfuser.child(firebaseUser.getUid()).setValue(user);

                                openLoginPage();

                            }else{
                                Toast.makeText(Signup.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this, "Please choose ....", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }
}
