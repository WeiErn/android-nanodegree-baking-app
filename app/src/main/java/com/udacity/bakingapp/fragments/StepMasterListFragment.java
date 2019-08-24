package com.udacity.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.StepActivity;
import com.udacity.bakingapp.adapters.StepMasterListAdapter;
import com.udacity.bakingapp.data.Ingredient;
import com.udacity.bakingapp.data.Step;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class StepMasterListFragment extends Fragment implements
        StepMasterListAdapter.StepAdapterOnClickHandler {

    // TODO: Customize parameter argument names

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private StepMasterListAdapter mStepMasterListAdapter;
    private RecyclerView mStepRecyclerView;
    private TextView mIngredientListTextView;
    private MediaPlayerFragment mMediaPlayerFragment;
    private boolean mTwoPane;
    private String mStepDescription;
    private String mVideoUrl;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepMasterListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StepMasterListFragment newInstance(int columnCount) {
        StepMasterListFragment fragment = new StepMasterListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getString("stepDescription") != null) {
                mStepDescription = savedInstanceState.getString("stepDescription");
            }
            if (savedInstanceState.getString("videoUrl") != null) {
                mVideoUrl = savedInstanceState.getString("videoUrl");
            }
            mTwoPane = savedInstanceState.getBoolean("twoPane");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_master_list, container, false);
        mIngredientListTextView = view.findViewById(R.id.ingredient_list);
        mStepRecyclerView = view.findViewById(R.id.list_steps);

        // Set the adapter
        if (mStepRecyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                mStepRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mStepRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mStepRecyclerView.setHasFixedSize(true);
            mStepMasterListAdapter = new StepMasterListAdapter(this);
            mStepRecyclerView.setAdapter(mStepMasterListAdapter);
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mStepDescription != null) {
            outState.putString("stepDescription", mStepDescription);
        }
        if (mVideoUrl != null) {
            outState.putString("videoUrl", mVideoUrl);
        }
        outState.putBoolean("twoPane", mTwoPane);
    }

    @Override
    public void onStepClick(Step step, String recipeName) {
        if (mMediaPlayerFragment != null) {
            mStepDescription = step.getDescription();
            mVideoUrl = step.getVideoUrl();
            mMediaPlayerFragment.setStepDescriptionView(mStepDescription);
            mMediaPlayerFragment.setVideoUriString(mVideoUrl, mTwoPane);
        } else {
            Context context = getActivity();
            Class destinationClass = StepActivity.class;
            Intent intentToStartStepActivity = new Intent(context, destinationClass);
            intentToStartStepActivity.putExtra(getString(R.string.intent_extra_step), step);
            intentToStartStepActivity.putExtra(getString(R.string.recipe_name), recipeName);
            startActivity(intentToStartStepActivity);
        }
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        // Set up TextView displaying list of ingredients
        StringBuffer stringBuffer = new StringBuffer();
        int ingredientListSize = ingredientList.size();
        for (int i = 0; i < ingredientListSize; i++) {
            Ingredient ingredient = ingredientList.get(i);
            stringBuffer.append("\u2022 " + ingredient.getQuantity() + " " +
                    ingredient.getMeasurement() + " " + ingredient.getIngredient());
            if (i < ingredientListSize - 1) {
                stringBuffer.append("\n");
            }
        }
        mIngredientListTextView.setText(stringBuffer.toString());
    }

    public void setupRecipeDataInAdapter(List<Step> stepData, String recipeName) {
        mStepMasterListAdapter.setStepData(stepData);
        mStepMasterListAdapter.setRecipeName(recipeName);
    }

    public void setMediaPlayerFragment(MediaPlayerFragment mediaPlayerFragment, boolean twoPane) {
        mMediaPlayerFragment = mediaPlayerFragment;
        mTwoPane = twoPane;
        mMediaPlayerFragment.setVideoUriString(mVideoUrl, mTwoPane);
        mMediaPlayerFragment.setStepDescriptionView(mStepDescription);
    }
}
