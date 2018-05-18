package com.advait.flickrphotoappv2.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.advait.flickrphotoappv2.R;
import com.advait.flickrphotoappv2.model.Photo;
import com.advait.flickrphotoappv2.ui.adapter.ImageAdapter;
import com.advait.flickrphotoappv2.util.FlickrApiUtils;
import com.advait.flickrphotoappv2.util.VolleyRequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoGridFragmentActivity extends AppCompatActivity {

    public static final String LIST_OF_PHOTOS = "LIST_OF_PHOTOS";
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageAdapter imageAdapter;
    private ArrayList<Photo> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            photoList = savedInstanceState.getParcelableArrayList(LIST_OF_PHOTOS);
        } else {
            photoList = new ArrayList<>();
        }
        progressBar = findViewById(R.id.progressBar);
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        imageAdapter = new ImageAdapter(photoList, this);
        recyclerView = findViewById(R.id.photoView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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

                String URL = FlickrApiUtils.getSearchPhotoinFlickrUrl(query, PhotoGridFragmentActivity.this);
                StringRequest flickrPhotoSearchRequest = new StringRequest(Request.Method.GET, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                photoList.clear();
                                progressBar.setVisibility(View.GONE);
                                parseSuccessResponse(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PhotoGridFragmentActivity.this, R.string.error_string, Toast.LENGTH_SHORT).show();
                        //TODO handle error scenario in case of any type of failure
                    }
                });

                VolleyRequestQueue.getInstance(PhotoGridFragmentActivity.this).addToRequestQueue(flickrPhotoSearchRequest);
                progressBar.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void parseSuccessResponse(String response) {
        try {
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
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setVisibility(View.VISIBLE);
            }
        } catch (JSONException je) {
            Log.e("FlickrPhotoApp", "Error parsing success response");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(LIST_OF_PHOTOS, photoList);
        super.onSaveInstanceState(outState);
    }
}
