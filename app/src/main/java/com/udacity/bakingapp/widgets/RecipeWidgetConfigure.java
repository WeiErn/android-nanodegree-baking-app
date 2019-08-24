package com.udacity.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.adapters.RecipeMasterGridAdapter;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.utils.JsonUtils;
import com.udacity.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

import static com.udacity.bakingapp.utils.NetworkUtils.isOnline;

public class RecipeWidgetConfigure extends AppCompatActivity implements
        RecipeMasterGridAdapter.RecipeAdapterOnClickHandler,
        LoaderCallbacks<List<Recipe>> {

    private static final String TAG = RecipeWidgetConfigure.class.getSimpleName();

    private static final String PREFS_NAME = "com.udacity.bakingapp.widgets.RecipeWidgetProvider";
    private static final String PREF_PREFIX_KEY = "prefix_";

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private TextView mNoInternetMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private static final int RECIPE_LOADER_ID = 0;

    private RecipeMasterGridAdapter mRecipeAdapter;
    private List<Recipe> mRecipes;
    private GridLayoutManager mLayoutManager;

    int mRecipeWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public RecipeWidgetConfigure() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        getSupportActionBar().setTitle(getResources().getString(R.string.widget_select_a_recipe));

        // Set the view layout resource to use.
        setContentView(R.layout.widget_recipe_configure);

        mRecyclerView = findViewById(R.id.widget_recycler_view_recipes);
        mErrorMessageDisplay = findViewById(R.id.widget_error_message_display);
        mNoInternetMessageDisplay = findViewById(R.id.widget_no_internet_connection_message_display);
        mLoadingIndicator = findViewById(R.id.widget_pb_loading_indicator);
        mLayoutManager = new GridLayoutManager(
                getApplicationContext(), 1, RecyclerView.VERTICAL, false
        );
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeMasterGridAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        // Bind the action for the save button.
//        findViewById(R.id.save_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mRecipeWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (mRecipeWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        initLoader();

//        mRecipeWidgetPrefix.setText(loadTitlePref(RecipeWidgetConfigure.this, mRecipeWidgetId));
    }

    private void initLoader() {
        int loaderId = RECIPE_LOADER_ID;
        LoaderCallbacks<List<Recipe>> callback = this;
        Bundle bundleForLoader = new Bundle();
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);
    }


//    View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            final Context context = RecipeWidgetConfigure.this;
//
//            // When the button is clicked, save the string in our prefs and return that they
//            // clicked OK.
//            String titlePrefix = mRecipeWidgetPrefix.getText().toString();
//            saveTitlePref(context, mRecipeWidgetId, titlePrefix);
//
//            // Push widget update to surface with newly set prefix
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            RecipeWidgetProvider.updateAppWidget(context, appWidgetManager,
//                    mRecipeWidgetId, titlePrefix);
//
//            // Make sure we pass back the original appWidgetId
//            Intent resultValue = new Intent();
//            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mRecipeWidgetId);
//            setResult(RESULT_OK, resultValue);
//            finish();
//        }
//    };
//

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<List<Recipe>>(this) {
            List<Recipe> mRecipesData = null;

            @Override
            protected void onStartLoading() {
                if (mRecipesData != null) {
                    deliverResult(mRecipesData);
                } else {
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public List<Recipe> loadInBackground() {
                URL recipesRequestUrl = NetworkUtils.buildUrl(getResources().getString(R.string.recipe_endpoint));
                try {
                    String jsonRecipesResponse = NetworkUtils
                            .getResponseFromHttpUrl(recipesRequestUrl);

                    List<Recipe> listRecipesData = JsonUtils
                            .getRecipesFromJson(jsonRecipesResponse);

                    return listRecipesData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(List<Recipe> data) {
                mRecipesData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> recipes) {
        mRecipes = recipes;
        mRecipeAdapter.setRecipeData(recipes);
        if (!isOnline(getApplicationContext())) {
            showNoInternetConnectionMessage();
        } else if (recipes == null) {
            showErrorMessage();
        } else {
            showRecipesDataView();
        }
        getSupportLoaderManager().destroyLoader(RECIPE_LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {

    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        final Context context = RecipeWidgetConfigure.this;

        // Push widget update to surface with newly set prefix
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RecipeWidgetProvider.updateAppWidget(context, appWidgetManager,
                mRecipeWidgetId, recipe);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mRecipeWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    private void showRecipesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoInternetMessageDisplay.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mNoInternetMessageDisplay.setVisibility(View.INVISIBLE);
    }

    private void showNoInternetConnectionMessage() {
        mNoInternetMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }
}
