package com.udacity.bakingapp.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.RecipeActivity;
import com.udacity.bakingapp.adapters.RecipeMasterGridAdapter;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.database.AppDatabase;
import com.udacity.bakingapp.utils.JsonUtils;
import com.udacity.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

import static com.udacity.bakingapp.utils.NetworkUtils.isOnline;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeMasterGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeMasterGridFragment extends Fragment implements
        RecipeMasterGridAdapter.RecipeAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<Recipe>> {

    private RecyclerView mRecyclerView;
    private RecipeMasterGridAdapter mRecipeMasterGridAdapter;
    private List<Recipe> mRecipes;
    private GridLayoutManager mLayoutManager;
    private TextView mErrorMessageDisplay;
    private TextView mNoInternetMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private static final String ARG_RECIPE_LAYOUT_ID = "recipe_layout";

    private int mRecipeLayoutId;

    private static final int MOVIE_LOADER_ID = 0;
    private AppDatabase mDb;

    @Override
    public void onRecipeClick(Recipe recipe) {
        Context context = getActivity();
        Class destinationClass = RecipeActivity.class;
        Intent intentToStartRecipeActivity = new Intent(context, destinationClass);
        intentToStartRecipeActivity.putExtra(getString(R.string.intent_extra_recipe), recipe);
        startActivity(intentToStartRecipeActivity);
    }

    public RecipeMasterGridFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipeLayoutId Parameter 1.
     * @return A new instance of fragment RecipeMasterGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeMasterGridFragment newInstance(String recipeLayoutId) {
        RecipeMasterGridFragment fragment = new RecipeMasterGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RECIPE_LAYOUT_ID, recipeLayoutId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipeLayoutId = getArguments().getInt(ARG_RECIPE_LAYOUT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRecipeLayoutId = getArguments().getInt(ARG_RECIPE_LAYOUT_ID);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_master_grid, container, false);
        mErrorMessageDisplay = view.findViewById(R.id.error_message_display);
        mNoInternetMessageDisplay = view.findViewById(R.id.no_internet_connection_message_display);
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);

        if (mRecipeLayoutId == R.id.list_recipes) {
            mRecyclerView = view.findViewById(R.id.list_recipes);
            mLayoutManager = new GridLayoutManager(
                    getActivity(), 1, RecyclerView.VERTICAL, false
            );
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeMasterGridAdapter = new RecipeMasterGridAdapter(this);
        mRecyclerView.setAdapter(mRecipeMasterGridAdapter);

        mDb = AppDatabase.getInstance(getActivity());
        setupRecipes();

        if (!isOnline(getActivity())) {
            showNoInternetConnectionMessage();
        } else {
            initLoader();
        }

        return view;
    }

    private void setupRecipes() {
        final LiveData<List<Recipe>> recipes = mDb.recipeListDao().loadAllRecipes();
        recipes.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mRecipes = recipes;
                mRecipeMasterGridAdapter.setRecipeData(mRecipes);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initLoader() {
        int loaderId = MOVIE_LOADER_ID;
        LoaderManager.LoaderCallbacks<List<Recipe>> callback = this;
        Bundle bundleForLoader = new Bundle();
        getLoaderManager().initLoader(loaderId, bundleForLoader, callback);
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable final Bundle bundle) {
        return new AsyncTaskLoader<List<Recipe>>(getActivity()) {
            List<Recipe> mRecipesData = null;

            @Override
            protected void onStartLoading() {
                if (mRecipesData != null) {
                    deliverResult(mRecipesData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public List<Recipe> loadInBackground() {
                URL recipesRequestUrl = NetworkUtils.buildUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
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
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecipes = recipes;
        mRecipeMasterGridAdapter.setRecipeData(recipes);
        if (!isOnline(getActivity())) {
            showNoInternetConnectionMessage();
        } else if (recipes == null) {
            showErrorMessage();
        } else {
            showRecipesDataView();
        }
        getLoaderManager().destroyLoader(MOVIE_LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {
    }

    private void invalidateData() {
        mRecipeMasterGridAdapter.setRecipeData(null);
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
