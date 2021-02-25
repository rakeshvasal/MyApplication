package com.example.rakeshvasal.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rakeshvasal.myapplication.Custom_Adapters.GalleryAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Album;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private List<Album> albumList;
    private List<String> listOfImagesPath;
    boolean albumdone=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new GalleryAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        new CreateAlbumAsync(GalleryActivity.this).execute();



        try {
            Glide.with(this)
                    .load(R.drawable.cover)
                    .into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
//showProgressDialog();
        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }



public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GalleryActivity.this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    public class CreateAlbumAsync extends AsyncTask<String,String,String>{

        Activity activity;

        public CreateAlbumAsync(Activity activity){
            this.activity=activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(albumdone) {
                closeProgressDialog();
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings) {

            prepareAlbums();

            return null;
        }

        private void prepareAlbums() {

            String ImagePaths = Environment.getExternalStorageDirectory() + "/Images/Khalsa Gang/";
            //String ImagePaths = Environment.getExternalStorageDirectory() + "/Instagram/";

            listOfImagesPath = null;
            listOfImagesPath = new Utils().RetriveCapturedImagePath(ImagePaths);

            if(listOfImagesPath!=null){
                int size = listOfImagesPath.size();
            /*if (size>50){
                size=50;
            }*/
                String[] coverimages=new String[size];
                for (int i=0;i<size;i++){
                    try {
                        //Bitmap bmp = (BitmapFactory.decodeFile(listOfImagesPath.get(i)));
                        coverimages[i]=listOfImagesPath.get(i);
                        //      coverimages[i] = bmp;
                        Album a = new Album("" + i, coverimages[i]);
                        albumList.add(a);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                albumdone=true;
            }
        }
    }

}
