//package com.udacity.bakingapp.widgets;
//
//import android.appwidget.AppWidgetManager;
//import android.arch.lifecycle.LiveData;
//import android.arch.lifecycle.Observer;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.udacity.bakingapp.R;
//import com.udacity.bakingapp.data.Recipe;
//import com.udacity.bakingapp.database.AppDatabase;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RecipeWidgetConfigure extends AppCompatActivity {
//    static final String TAG = "RecipeWidgetConfigure";
//
//    private static final String PREFS_NAME = "com.udacity.bakingapp.widgets.RecipeWidgetProvider";
//    private static final String PREF_PREFIX_KEY = "prefix_";
//    private AppDatabase mDb;
//    private List<Recipe> mRecipes;
//
//    int mRecipeWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
//    TextView mRecipeWidgetPrefix;
//
//    public RecipeWidgetConfigure() {
//        super();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Set the result to CANCELED.  This will cause the widget host to cancel
//        // out of the widget placement if they press the back button.
//        setResult(RESULT_CANCELED);
//
//        // Set the view layout resource to use.
//        setContentView(R.layout.recipe_widget_configure);
//
//        // Load all recipes
//        loadAllRecipes();
//
//        // Find the EditText
//        mRecipeWidgetPrefix = findViewById(R.id.recipe_widget_prefix);
//
//        // Bind the action for the save button.
//        findViewById(R.id.save_button).setOnClickListener(mOnClickListener);
//
//        // Find the widget id from the intent.
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            mRecipeWidgetId = extras.getInt(
//                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//        }
//
//        if (mRecipeWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
//            finish();
//        }
//
//        mRecipeWidgetPrefix.setText(loadTitlePref(RecipeWidgetConfigure.this, mRecipeWidgetId));
//    }
//
//    private void loadAllRecipes() {
//        mDb = AppDatabase.getInstance(getApplicationContext());
//        final LiveData<List<Recipe>> recipes = mDb.recipeDao().loadAllRecipes();
//        recipes.observe(this, new Observer<List<Recipe>>() {
//            @Override
//            public void onChanged(@Nullable List<Recipe> recipes) {
//                mRecipes = recipes;
//            }
//        });
//    }
//
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
//    // Write the prefix to the SharedPreferences object for this widget
//    static void saveTitlePref(Context context, int appWidgetId, String text) {
//        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
//        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
//        prefs.commit();
//    }
//    // Read the prefix from the SharedPreferences object for this widget.
//    // If there is no preference saved, get the default from a resource
//    static String loadTitlePref(Context context, int appWidgetId) {
//        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
//        String prefix = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
//        if (prefix != null) {
//            return prefix;
//        } else {
//            return context.getString(R.string.appwidget_prefix_default);
//        }
//    }
//    static void deleteTitlePref(Context context, int appWidgetId) {
//    }
//    static void loadAllTitlePrefs(Context context, ArrayList<Integer> appWidgetIds,
//                                  ArrayList<String> texts) {
//    }
//}
