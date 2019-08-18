package com.udacity.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.udacity.bakingapp.data.Step;
import com.udacity.bakingapp.fragments.MediaPlayerFragment;

public class StepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.intent_extra_step)) && intent.hasExtra(getString(R.string.recipe_name))) {

            String recipeName = intent.getExtras().getString(getString(R.string.recipe_name));
            getSupportActionBar().setTitle(recipeName);

            Step step = intent.getExtras().getParcelable(getString(R.string.intent_extra_step));
            String videoUrl = step.getVideoUrl();

            FragmentManager fragmentManager = getSupportFragmentManager();
            MediaPlayerFragment mediaPlayerFragment = (MediaPlayerFragment) fragmentManager.findFragmentById(R.id.step_video_player_fragment);

            mediaPlayerFragment.setStepDescriptionView(step.getDescription());

            if (!videoUrl.isEmpty()) {
                mediaPlayerFragment.setVideoUriString(videoUrl);
            }
        }
    }
}
