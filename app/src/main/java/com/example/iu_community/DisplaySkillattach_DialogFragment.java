package com.example.iu_community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

public class DisplaySkillattach_DialogFragment extends DialogFragment {
    String imgurl;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_displayskillattach,container,false);
        TouchImageView imageView = v.findViewById(R.id.showingImage);

        // Library for displaying the image from the url
        Picasso.get().load(getImgurl()).into(imageView);

        return v;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) { this.imgurl = imgurl; }
}
