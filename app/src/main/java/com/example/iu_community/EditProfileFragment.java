package com.example.iu_community;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {
    ImageView backimg,userimg,updateusername,updateusermajor,updateuserlevel,skillimg,addskillimg;
    TextView username;
    EditText major,level,skilltitle,skillrate;

    FirebaseAuth auth;

    String userid;

    StorageReference storageRef;

    public final int PROFILE_PICK=101;
    public final int PROFILE_PICK2=102;

    DatabaseReference dfuser,dfskill;

    Uri skillimguri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.profileedit_fragment,container,false);

        auth = FirebaseAuth.getInstance();

        storageRef = FirebaseStorage.getInstance().getReference();

        userid = auth.getCurrentUser().getUid();


        backimg = view.findViewById(R.id.editprofile_backimg);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frag,new ProfileFragment()).commit();
            }
        });

        userimg = view.findViewById(R.id.editprofile_userimg);

        username = view.findViewById(R.id.editprofile_username);

        updateusername = view.findViewById(R.id.editprofile_updateusername);

        major = view.findViewById(R.id.editprofile_major);
        updateusermajor = view.findViewById(R.id.editprofile_updateusermajor);

        level = view.findViewById(R.id.editprofile_level);
        updateuserlevel = view.findViewById(R.id.editprofile_updateuserlevel);



        dfuser = FirebaseDatabase.getInstance().getReference().child("user").child(userid);

        dfuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user.getUserimage().equals("default")) {
                    userimg.setImageResource(R.drawable.person_icon_foreground);
                }else{
                    Picasso.get().load(user.getUserimage()).into(userimg);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //choose the image
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PROFILE_PICK);
            }
        });

        updateusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Filled is empty", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Are you sure to update this field?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dfuser = FirebaseDatabase.getInstance().getReference().child("user").child(userid).child("username");
                            dfuser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().setValue(username.getText().toString());
                                    Toast.makeText(getActivity(), "Updated!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        updateusermajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(major.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Filled is empty", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Are you sure to update this field?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dfuser = FirebaseDatabase.getInstance().getReference().child("user").child(userid).child("major");
                            dfuser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().setValue(major.getText().toString());
                                    Toast.makeText(getActivity(), "Updated!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });

        updateuserlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Filled is empty", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Are you sure to update this field?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dfuser = FirebaseDatabase.getInstance().getReference().child("user").child(userid).child("level");
                            dfuser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().setValue(level.getText().toString());
                                    Toast.makeText(getActivity(), "Updated!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });

        skilltitle = view.findViewById(R.id.editprofile_skilltitle_edtxt);
        skillrate = view.findViewById(R.id.editprofile_skillrate_edtxt);

        skillimg = view.findViewById(R.id.editprofile_skillimg);

        skillimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //choose the image
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PROFILE_PICK2);
            }
        });

        addskillimg = view.findViewById(R.id.editprofile_addskill);

        addskillimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // FirebaseAuth.getInstance().signOut();
                if(skilltitle.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please write a title for the skill", Toast.LENGTH_SHORT).show();
                }else if(skillrate.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please write a rate for the skill", Toast.LENGTH_SHORT).show();
                }else if(getSkillimguri()==null){
                    Toast.makeText(getActivity(), "Please upload a picture for the skill", Toast.LENGTH_SHORT).show();
                }else
                    uploadTodatabase(getSkillimguri(), "skill");
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PROFILE_PICK&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            Uri uri = data.getData();
            userimg.setImageURI(uri);
            uploadTodatabase(uri,"user");
        }else if(requestCode==PROFILE_PICK2&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            Uri uri = data.getData();
            skillimg.setBackground(null);
            skillimg.setImageURI(uri);
            setSkillimguri(uri);
        }else{
            Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }
    public void uploadTodatabase(Uri uri,String imgtype){
        // upload the image to the firebase storage

        if(imgtype.equals("user")) {
            StorageReference imgref = storageRef.child("images/profile_images/p" + (int) (Math.random() * 10));

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Changing your profile image");
            progressDialog.show();
            imgref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    imgref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //set the user image as url
                            DatabaseReference df = FirebaseDatabase.getInstance().getReference("user").child(userid);
                            df.child("userimage").setValue(uri.toString());
                        }
                    });
                    Toast.makeText(getActivity(), "setting profile image is done", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "setting profile image error", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressDialog.setMessage("Processing...");
                }
            });
        }else if(imgtype.equals("skill")){
            StorageReference imgref = storageRef.child("images/skill_images/s" + (int) (Math.random() * 10));

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("set your skill info");
            progressDialog.show();
            imgref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    imgref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //set the user image as url
                            String skillid = UUID.randomUUID().toString();

                            Skill skill = new Skill(skillid,skilltitle.getText().toString(),skillrate.getText().toString(),uri.toString(),userid);
                            dfskill = FirebaseDatabase.getInstance().getReference();
                            dfskill.child("skill").push().setValue(skill);

                        }
                    });
                    Toast.makeText(getActivity(), "setting skill info is done", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "setting skill info error", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressDialog.setMessage("Processing...");
                }
            });
        }
    }

    public Uri getSkillimguri() {
        return skillimguri;
    }

    public void setSkillimguri(Uri skillimguri) {
        this.skillimguri = skillimguri;
    }
}
