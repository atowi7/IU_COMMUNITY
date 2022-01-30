package com.example.iu_community;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Message_p extends AppCompatActivity {
    ImageView backimg,userimage, sendimg,shareimg;
    TextView username;
    EditText mEdit;
    Intent intent;
    String userid;
    String suserid;
    public String msgtxt;
   public  String std="";
   public boolean c=false;
    public static boolean active=false;
    public final int CHAT_PICK=102;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    List<Chat> chatList;

    FirebaseAuth auth;
    StorageReference storageRef;

    @Override
    protected void onStart() {
        super.onStart();
        Message_p.active=true;
        startService(new Intent(this,Service.class));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_page);

        backimg = (ImageView) findViewById(R.id.msg_backimg);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userimage = (ImageView) findViewById(R.id.user_m_image);
        username = (TextView) findViewById(R.id.user_m_name);
        mEdit = (EditText) findViewById(R.id.msend_editt);
        sendimg = (ImageView) findViewById(R.id.send_btn);
        shareimg = (ImageView) findViewById(R.id.share_btn);

        recyclerView = (RecyclerView) findViewById(R.id.recycleview_msg);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        auth = FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid();

        intent = getIntent();
        suserid = intent.getStringExtra("userid");

        storageRef = FirebaseStorage.getInstance().getReference();

        //set profile
            DatabaseReference df = FirebaseDatabase.getInstance().getReference("user").child(suserid);
            df.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    username.setText(user.getUsername());
                    if (user.getUserimage().equals("default")) {
                        userimage.setImageResource(R.drawable.person_icon_foreground);
                    }else{
                        Picasso.get().load(user.getUserimage()).into(userimage);
                    }

                    readMsg(userid, suserid, user.getUserimage());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        //share the image
        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //choose the image
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,CHAT_PICK);
            }
        });

        //Send the message
        sendimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdit.equals("")) {
                    Toast.makeText(getApplicationContext(), "Filled is empty", Toast.LENGTH_SHORT).show();
                } else {
                    msgtxt = mEdit.getText().toString();

                        DatabaseReference df2 = FirebaseDatabase.getInstance().getReference();

                        String did = UUID.randomUUID().toString();

                        HashMap<String, String> minfo = new HashMap<>();
                        minfo.put("messagetxt", msgtxt);
                        minfo.put("messageimg", "no_sora");
                        minfo.put("chatallid", "");
                        minfo.put("isseen", "false");
                        minfo.put("did", did);
                        minfo.put("isdeleted", "false");
                        minfo.put("sender", userid);
                        minfo.put("receiver", suserid);

                        df2.child("Chat").push().setValue(minfo);

                        //Add the user id to the notification table to receive the notification by the another user
                        DatabaseReference df5 = FirebaseDatabase.getInstance().getReference("Notification").child(suserid).child(userid);
                        df5.child("id").setValue(userid);

                    }

                //Set the edittext value to empty after sending the message
                mEdit.setText("");
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Get the image url after selecting it from the gallery
        if(requestCode==CHAT_PICK&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            Uri uri = data.getData(); //Image url

            uploadTodatabase(uri);
        }else{
            Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }
    public void uploadTodatabase(Uri uri){
        // upload the image to the firebase storage
        StorageReference imgref = storageRef.child("images/chat_images/c"+(int) (Math.random() * 10));

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sending image");
        progressDialog.show();

        imgref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                            DatabaseReference df2 = FirebaseDatabase.getInstance().getReference();

                            String did = UUID.randomUUID().toString();

                            HashMap<String, String> minfo = new HashMap<>();
                            minfo.put("messagetxt", "no_nas");
                            minfo.put("messageimg", uri.toString());
                            minfo.put("isseen", "false");
                            minfo.put("did", did);
                            minfo.put("isdeleted", "false");
                            minfo.put("sender", userid);
                            minfo.put("receiver", suserid);

                            df2.child("Chat").push().setValue(minfo);

                            DatabaseReference df3 = FirebaseDatabase.getInstance().getReference("ChatList").child(userid).child(suserid);
                            df3.child("id").setValue(suserid);

                            DatabaseReference df4 = FirebaseDatabase.getInstance().getReference("ChatList").child(suserid).child(userid);
                            df4.child("id").setValue(userid);

                            DatabaseReference df5 = FirebaseDatabase.getInstance().getReference("Notification").child(suserid).child(userid);
                            df5.child("id").setValue(userid);

                    }
                });
               progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
             progressDialog.setMessage("Processing...");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error in sending image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void readMsg(String userid, String suserid, String msgurl) {
       // method for reading the message
        chatList = new ArrayList<>();

            DatabaseReference df = FirebaseDatabase.getInstance().getReference("Chat");

            df.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    chatList.clear();

                    for (DataSnapshot s : snapshot.getChildren()) {
                        Chat chat = s.getValue(Chat.class);

                            if ((chat.getSender().equals(userid) && chat.getReceiver().equals(suserid)) || (chat.getSender().equals(suserid) && chat.getReceiver().equals(userid))) {

                                chatList.add(chat);

                        }

                        messageAdapter = new MessageAdapter(Message_p.this, chatList, msgurl);

                        recyclerView.setAdapter(messageAdapter);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        openHome();
    }

    public void openHome() {
        // open home page
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();

    }

}