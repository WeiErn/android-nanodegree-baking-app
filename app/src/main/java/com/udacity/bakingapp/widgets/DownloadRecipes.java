package com.udacity.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
    private RemoteViews mViews;
    private int mWidgetId;
    private AppWidgetManager mWidgetManager;
    private Context mContext;

    public DownloadRecipes(Context context, RemoteViews views, int appWidgetID, AppWidgetManager appWidgetManager) {
        this.mContext = context;
        this.mViews = views;
        this.mWidgetId = appWidgetID;
        this.mWidgetManager = appWidgetManager;
    }

    @Override
    protected List<Recipe> doInBackground(Void... voids) {
        URL recipesRequestUrl = NetworkUtils.buildUrl(Resources.getSystem().getString(R.string.recipe_endpoint));
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
        String result = null;
        for (Recipe recipe : recipeList) {
            result.concat(recipe.getName() + " ");
        }
        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        mViews.setTextViewText(R.id.appwidget_text, result);

        mViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        mWidgetManager.updateAppWidget(mWidgetId, mViews);
    }
}
