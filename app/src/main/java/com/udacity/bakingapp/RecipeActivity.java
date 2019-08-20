package com.udacity.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.data.Ingredient;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.data.Step;
import com.udacity.bakingapp.database.AppDatabase;
import com.udacity.bakingapp.database.AppExecutors;
import com.udacity.bakingapp.fragments.MediaPlayerFragment;
import com.udacity.bakingapp.fragments.StepMasterListFragment;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private AppDatabase mDb;
    private boolean mTwoPane;
    private StepMasterListFragment mStepMasterListFragment;
    private MediaPlayerFragment mMediaPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.intent_extra_recipe))) {
            Recipe recipe = intent.getExtras().getParcelable(getString(R.string.intent_extra_recipe));

            getSupportActionBar().setTitle(recipe.getName());

            if (findViewById(R.id.step_media_player_fragment) != null) {
                mTwoPane = true;
            } else {
                mTwoPane = false;
            }

            setupRecipe(recipe);

            // empty database and insert new recipe
            addRecipeToDb(recipe);
        }
    }

    protected void setupRecipe(Recipe recipe) {
        List<Ingredient> ingredientList = recipe.getIngredientList();
        List<Step> stepList = recipe.getStepList();
        String recipeName = recipe.getName();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mStepMasterListFragment = (StepMasterListFragment) fragmentManager.findFragmentById(R.id.step_master_list_fragment);
        mStepMasterListFragment.setIngredientList(ingredientList);
        mStepMasterListFragment.setupRecipeDataInAdapter(stepList, recipeName);

        if (mTwoPane) {
            mMediaPlayerFragment = (MediaPlayerFragment) fragmentManager.findFragmentById(R.id.step_media_player_fragment);
            mStepMasterListFragment.setMediaPlayerFragment(mMediaPlayerFragment, mTwoPane);
        }
    }

    protected void addRecipeToDb(final Recipe recipe) {
        mDb = AppDatabase.getInstance(getApplicationContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.recipeDao().deleteAllRecipes();
                mDb.recipeDao().insertRecipe(recipe);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mTwoPane) {
            getSupportFragmentManager().putFragment(outState, "mediaPlayerFragment", mMediaPlayerFragment);
        }
    }

//    @Override
//    public void onRestoreInstanceState(Bundle inState) {
//        mMediaPlayerFragment = getFragmentManager().getFragment(inState, "mediaPlayerFragment");
//    }
}
