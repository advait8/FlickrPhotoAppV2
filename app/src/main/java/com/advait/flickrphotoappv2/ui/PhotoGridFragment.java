package com.advait.flickrphotoappv2.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.advait.flickrphotoappv2.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PhotoGridFragment extends Fragment {

    public PhotoGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_grid_fragment, container, false);
    }
}
