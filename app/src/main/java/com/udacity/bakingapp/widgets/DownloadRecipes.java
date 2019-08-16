package com.udacity.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.udacity.bakingapp.MainActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.utils.JsonUtils;
import com.udacity.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class DownloadRecipes extends AsyncTask<Void, Void, List<Recipe>> {
    private int mWidgetId;
    private AppWidgetManager mWidgetManager;
    private Context mContext;

    public DownloadRecipes(Context context, int appWidgetID, AppWidgetManager appWidgetManager) {
        this.mContext = context;
        this.mWidgetId = appWidgetID;
        this.mWidgetManager = appWidgetManager;
    }

    @Override
    protected List<Recipe> doInBackground(Void... voids) {
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

    @Override
    protected void onPostExecute(List<Recipe> recipeList) {
//        StringBuilder result = new StringBuilder();
//        for (Recipe recipe : recipeList) {
//            String recipeName = recipe.getName();
//            result.append(recipeName);
//            result.append(" ");
//        }
        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

//        mViews.setTextViewText(R.id.appwidget_text, result);
//        mViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_provider);
        for (Recipe recipe : recipeList) {
            RemoteViews recipeView = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_list_item);
            recipeView.setTextViewText(R.id.recipe_name, recipe.getName());
            views.addView(R.id.widget_recipe_list, recipeView);
        }
        views.setOnClickPendingIntent(R.id.widget_recipe_list, pendingIntent);
        mWidgetManager.updateAppWidget(mWidgetId, views);
    }
}
