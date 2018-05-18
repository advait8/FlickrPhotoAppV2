package com.advait.flickrphotoappv2.util;

import android.content.Context;

import com.advait.flickrphotoappv2.R;

public class FlickrApiUtils {
    public static final String FLICKR_API_URL = "https://api.flickr.com/services/rest/?method=";

    public static final String SEARCH_PHOTO_API_METHOD = "flickr.photos.search";

    public static final String API_KEY = "&api_key=";

    public static final String QUERY_STRING = "&text=";

    public static final String API_RESPONSE_FORMAT = "&extras=url_s&format=json&nojsoncallback=1";

    public static String getSearchPhotoinFlickrUrl(String query, Context context) {
        return FlickrApiUtils.FLICKR_API_URL
                + FlickrApiUtils.SEARCH_PHOTO_API_METHOD
                + FlickrApiUtils.API_KEY
                + context.getString(R.string.flickr_api_key)
                + FlickrApiUtils.QUERY_STRING + query
                + FlickrApiUtils.API_RESPONSE_FORMAT;
    }
}
