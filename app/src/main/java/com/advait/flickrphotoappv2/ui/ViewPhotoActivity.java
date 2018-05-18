package com.advait.flickrphotoappv2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.advait.flickrphotoappv2.R;
import com.advait.flickrphotoappv2.model.Photo;
import com.advait.flickrphotoappv2.ui.adapter.ImageAdapter;
import com.advait.flickrphotoappv2.util.GlideApp;

public class ViewPhotoActivity extends AppCompatActivity {

    private ImageView enlargedPhoto;
    private Photo selectedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        enlargedPhoto = findViewById(R.id.enlargedPhoto);
        if (savedInstanceState != null) {
            selectedPhoto = savedInstanceState.getParcelable(ImageAdapter.SELECTED_PHOTO);
        } else {
            selectedPhoto = getIntent().getParcelableExtra(ImageAdapter.SELECTED_PHOTO);
        }
        GlideApp.with(this)
                .load(selectedPhoto.getUrlString())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(enlargedPhoto);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ImageAdapter.SELECTED_PHOTO, selectedPhoto);
        super.onSaveInstanceState(outState);
    }
}
