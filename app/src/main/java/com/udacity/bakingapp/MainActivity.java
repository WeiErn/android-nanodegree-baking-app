package com.udacity.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.udacity.bakingapp.fragments.RecipeMasterGridFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        int containerId = 0;

        if (findViewById(R.id.recipe_master_list_fragment_container) != null) {
            bundle.putInt("recipe_layout", R.id.recipe_master_list_fragment_container);
            containerId = R.id.recipe_master_list_fragment_container;
        } else if (findViewById(R.id.recipe_master_grid_fragment_container) != null) {
            bundle.putInt("recipe_layout", R.id.recipe_master_grid_fragment_container);
            containerId = R.id.recipe_master_grid_fragment_container;
        }

        RecipeMasterGridFragment recipeMasterGridFragment = new RecipeMasterGridFragment();
        recipeMasterGridFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(containerId, recipeMasterGridFragment)
                .commit();
    }
}
