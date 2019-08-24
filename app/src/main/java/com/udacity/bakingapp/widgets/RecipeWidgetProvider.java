package com.udacity.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.udacity.bakingapp.MainActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.Ingredient;
import com.udacity.bakingapp.data.Recipe;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider implements LifecycleOwner {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Recipe recipe) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_provider);
        views.setTextViewText(R.id.widget_recipe_name, recipe.getName());

        List<Ingredient> ingredientList = recipe.getIngredientList();

        for (Ingredient ingredient : ingredientList) {
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredient_item);
            ingredientView.setTextViewText(R.id.widget_ingredient_item,
                    ingredient.getQuantity() + " "
                            + ingredient.getMeasurement() + " "
                            + ingredient.getIngredient());
            views.addView(R.id.widget_ingredient_container, ingredientView);
        }

        views.setOnClickPendingIntent(R.id.widget_ingredient_container, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            new LoadLastSeenRecipeTask(context, appWidgetId, appWidgetManager).execute();
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipe) {
        for (int appWidgetId : appWidgetIds) {
//            new LoadLastSeenRecipeTask(context, appWidgetId, appWidgetManager).execute();
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
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

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}

