package com.udacity.bakingapp;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.bakingapp.adapters.RecipeAdapter;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.fragments.RecipeMasterGridFragment;
import com.udacity.bakingapp.utils.JsonUtils;
import com.udacity.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

import static com.udacity.bakingapp.utils.NetworkUtils.isOnline;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private List<Recipe> mRecipes;

    private boolean mTabletLayout;
    private GridLayoutManager mLayoutManager;

    private TextView mErrorMessageDisplay;
    private TextView mNoInternetMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private static final int MOVIE_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putInt("recipe_layout", R.id.list_recipes);

        RecipeMasterGridFragment recipeMasterGridFragment = new RecipeMasterGridFragment();
        recipeMasterGridFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_master_grid_fragment_container, recipeMasterGridFragment)
                .commit();


//        mErrorMessageDisplay = findViewById(R.id.error_message_display);
//        mNoInternetMessageDisplay = findViewById(R.id.no_internet_connection_message_display);
//        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

//        String jsonResult = getJsonStringFromAsset(this,"baking.json");

//        if (findViewById(R.id.baking_app_grid_layout) != null) {

//        if (findViewById(R.id.list_recipes) != null) {
//
//            mRecyclerView = findViewById(R.id.list_recipes);
//            mLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
//        } else if (findViewById(R.id.grid_recipes) != null) {
//
//            mTabletLayout = true;
//            mRecyclerView = findViewById(R.id.grid_recipes);
//            mLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
//        }
//
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        mRecipeAdapter = new RecipeAdapter(this);
//        mRecyclerView.setAdapter(mRecipeAdapter);
//
//        if (!isOnline(this)) {
//            showNoInternetConnectionMessage();
//        } else {
//            initLoader();
//        }
    }


//    private void initLoader() {
//        int loaderId = MOVIE_LOADER_ID;
//        LoaderCallbacks<List<Recipe>> callback = MainActivity.this;
//        Bundle bundleForLoader = new Bundle();
//        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);
//    }
//
//    @Override
//    public void onClick(Recipe recipe) {
////        Context context = this;
////        Class destinationClass = RecipeActivity.class;
////        Intent intentToStartRecipeActivity = new Intent(context, destinationClass);
////        intentToStartRecipeActivity.putExtra("recipe", recipe);
////        startActivity(intentToStartRecipeActivity);
//        Toast.makeText(getApplicationContext(), "You've clicked on a recipe", Toast.LENGTH_SHORT).show();
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    @NonNull
//    @Override
//    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable final Bundle bundle) {
//        return new AsyncTaskLoader<List<Recipe>>(this) {
//            List<Recipe> mRecipesData = null;
//
//            @Override
//            protected void onStartLoading() {
//                if (mRecipesData != null) {
//                    deliverResult(mRecipesData);
//                } else {
//                    mLoadingIndicator.setVisibility(View.VISIBLE);
//                    forceLoad();
//                }
//            }
//
//            @Nullable
//            @Override
//            public List<Recipe> loadInBackground() {
//                URL recipesRequestUrl = NetworkUtils.buildUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
//                try {
//                    String jsonRecipesResponse = NetworkUtils
//                            .getResponseFromHttpUrl(recipesRequestUrl);
//
//                    List<Recipe> listRecipesData = JsonUtils
//                            .getRecipesFromJson(jsonRecipesResponse);
//
//                    return listRecipesData;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//
//            public void deliverResult(List<Recipe> data) {
//                mRecipesData = data;
//                super.deliverResult(data);
//            }
//        };
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> recipes) {
//        mLoadingIndicator.setVisibility(View.INVISIBLE);
//        mRecipes = recipes;
//        mRecipeAdapter.setRecipeData(recipes);
//        if (!isOnline(this)) {
//            showNoInternetConnectionMessage();
//        } else if (recipes == null) {
//            showErrorMessage();
//        } else {
//            showRecipesDataView();
//        }
//        getSupportLoaderManager().destroyLoader(MOVIE_LOADER_ID);
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {
//    }
//
//    private void invalidateData() {
//        mRecipeAdapter.setRecipeData(null);
//    }
//
//    private void showRecipesDataView() {
//        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
//        mRecyclerView.setVisibility(View.VISIBLE);
//        mNoInternetMessageDisplay.setVisibility(View.INVISIBLE);
//    }
//
//    private void showErrorMessage() {
//        mErrorMessageDisplay.setVisibility(View.VISIBLE);
//        mRecyclerView.setVisibility(View.INVISIBLE);
//        mNoInternetMessageDisplay.setVisibility(View.INVISIBLE);
//    }
//
//    private void showNoInternetConnectionMessage() {
//        mNoInternetMessageDisplay.setVisibility(View.VISIBLE);
//        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
//        mRecyclerView.setVisibility(View.INVISIBLE);
//    }
}
