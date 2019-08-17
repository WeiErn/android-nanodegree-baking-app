package com.udacity.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.udacity.bakingapp.MainActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.Ingredient;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.database.AppDatabase;

import java.util.List;

public class LoadLastSeenRecipeTask extends AsyncTask<Void, Void, Recipe> {
    private int mWidgetId;
    private AppWidgetManager mWidgetManager;
    private Context mContext;

    public LoadLastSeenRecipeTask(Context context, int appWidgetID, AppWidgetManager appWidgetManager) {
        this.mContext = context;
        this.mWidgetId = appWidgetID;
        this.mWidgetManager = appWidgetManager;
    }

    @Override
    protected Recipe doInBackground(Void... voids) {
        AppDatabase appDatabase = AppDatabase.getInstance(mContext);
        Recipe recipe = appDatabase.recipeDao().loadLastSeenRecipe();
        return recipe;
    }

    @Override
    protected void onPostExecute(Recipe recipe) {
        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_provider);
        views.setTextViewText(R.id.widget_recipe_name, recipe.getName());

        List<Ingredient> ingredientList = recipe.getIngredientList();

        for (Ingredient ingredient : ingredientList) {
            RemoteViews ingredientView = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_ingredient_item);
            ingredientView.setTextViewText(R.id.widget_ingredient_item,
                    ingredient.getQuantity() + " "
                            + ingredient.getMeasurement() + " "
                            + ingredient.getIngredient());
            views.addView(R.id.widget_ingredient_container, ingredientView);
        }

        views.setOnClickPendingIntent(R.id.widget_ingredient_container, pendingIntent);
        mWidgetManager.updateAppWidget(mWidgetId, views);
    }
}
