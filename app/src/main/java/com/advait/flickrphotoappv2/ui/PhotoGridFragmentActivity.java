package com.advait.flickrphotoappv2.ui;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.advait.flickrphotoappv2.R;
import com.advait.flickrphotoappv2.model.Photo;
import com.advait.flickrphotoappv2.ui.adapter.ImageAdapter;
import com.advait.flickrphotoappv2.util.FlickrApiConstants;
import com.advait.flickrphotoappv2.util.VolleyRequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhotoGridFragmentActivity extends AppCompatActivity {

    private SearchView searchView;

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageAdapter = new ImageAdapter(null,this);
        recyclerView = findViewById(R.id.photoView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_grid, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();

                String URL = getSearchPhotoinFlickrUrl(query);
                StringRequest flickrPhotoSearchRequest = new StringRequest(Request.Method.GET, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                parseSuccessResponse(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO handle error scenario in case of any type of failure
                    }
                });

                VolleyRequestQueue.getInstance(PhotoGridFragmentActivity.this).addToRequestQueue(flickrPhotoSearchRequest);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @NonNull
    private String getSearchPhotoinFlickrUrl(String query) {
        return FlickrApiConstants.FLICKR_API_URL
                + FlickrApiConstants.SEARCH_PHOTO_API_METHOD
                + FlickrApiConstants.API_KEY
                + getString(R.string.flickr_api_key)
                + FlickrApiConstants.QUERY_STRING + query
                + FlickrApiConstants.API_RESPONSE_FORMAT;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void parseSuccessResponse(String response) {
        try {
            List<Photo> photoList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray photoJsonArray = jsonObject.optJSONObject("photos").optJSONArray("photo");
            if (photoJsonArray != null) {
                for (int i = 0; i < photoJsonArray.length(); i++) {
                    JSONObject tempJsonObject = photoJsonArray.optJSONObject(i);
                    int id = tempJsonObject.optInt("id");
                    String title = tempJsonObject.optString("title");
                    String urlString = tempJsonObject.optString("url_s");
                    int height = tempJsonObject.optInt("height");
                    int width = tempJsonObject.optInt("width");
                    Photo tempPhoto = new Photo(id, title, urlString, height, width);
                    photoList.add(tempPhoto);
                }
            }

            if (photoList.size() > 0) {
                imageAdapter = new ImageAdapter(photoList, this);
                recyclerView.setAdapter(imageAdapter);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } catch (JSONException je) {
            Log.e("FlickrPhotoApp", "Error parsing success response");
        }
    }
}
