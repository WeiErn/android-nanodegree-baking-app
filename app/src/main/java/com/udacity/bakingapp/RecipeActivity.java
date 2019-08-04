package com.udacity.bakingapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.data.Step;
import com.udacity.bakingapp.fragments.StepMasterListFragment;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.intent_extra_recipe))) {
            Recipe recipe = intent.getExtras().getParcelable(getString(R.string.intent_extra_recipe));
            List<Step> stepList = recipe.getStepList();

            FragmentManager fragmentManager = getSupportFragmentManager();
            StepMasterListFragment stepMasterListFragment = (StepMasterListFragment) fragmentManager.findFragmentById(R.id.step_master_list_fragment);
            stepMasterListFragment.setStepAdapterData(stepList);
        }

    }
}
