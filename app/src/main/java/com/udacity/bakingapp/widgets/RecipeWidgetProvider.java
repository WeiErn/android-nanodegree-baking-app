package com.udacity.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.udacity.bakingapp.MainActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.database.AppDatabase;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private AppDatabase mDb;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Get the count from prefs.
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_prefs_file), 0);
        int count = sharedPreferences.getInt(context.getString(R.string.count_widget_key) + appWidgetId, 0);
        count++;

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Save count back to prefs.
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putInt(context.getString(R.string.count_widget_key) + appWidgetId, count);
        prefEditor.apply();

        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mDb = AppDatabase.getInstance(context);
        List<Recipe> recipes = mDb.recipeListDao().loadAllRecipes().getValue();
        
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
