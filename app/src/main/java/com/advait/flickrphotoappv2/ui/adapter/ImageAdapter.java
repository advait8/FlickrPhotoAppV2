package com.advait.flickrphotoappv2.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.advait.flickrphotoappv2.R;
import com.advait.flickrphotoappv2.model.Photo;
import com.advait.flickrphotoappv2.ui.ViewPhotoActivity;
import com.advait.flickrphotoappv2.util.GlideApp;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.vh> {
    public static final String SELECTED_PHOTO = "SELECTED_PHOTO";
    private List<Photo> photos;
    private Context context;

    public ImageAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    public class vh extends RecyclerView.ViewHolder {
        protected ImageView imageView;

        public vh(View v) {
            super(v);
            imageView = v.findViewById(R.id.imageView);

        }
    }

    @NonNull
    @Override
    public ImageAdapter.vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row_layout, parent, false);
        return new vh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull vh holder, int position) {
        if (photos != null) {
            final Photo selectedPhoto = photos.get(position);
            String url = selectedPhoto.getUrlString();

            GlideApp.with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.imageView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                holder.imageView.setTooltipText(selectedPhoto.getTitle());
            }
            holder.imageView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent viewPhotoIntent = new Intent(context, ViewPhotoActivity.class);
                    viewPhotoIntent.putExtra(SELECTED_PHOTO,selectedPhoto);
                    context.startActivity(viewPhotoIntent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return photos != null ? photos.size() : 0;
    }
}
