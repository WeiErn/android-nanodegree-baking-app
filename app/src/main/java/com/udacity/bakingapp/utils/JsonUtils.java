package com.udacity.bakingapp.utils;

import android.content.Context;

import com.udacity.bakingapp.data.Ingredient;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.data.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {

    public static String getJsonStringFromAsset(Context context, String jsonFile) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(jsonFile);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static List getRecipesFromJson(String jsonStr)
            throws JSONException {

        List recipeList = new ArrayList<Recipe>();
        JSONArray recipesJsonArr = new JSONArray(jsonStr);

        if (recipesJsonArr.length() > 0) {
            for (int i = 0; i < recipesJsonArr.length(); i++) {
                JSONObject recipeJsonObj = recipesJsonArr.getJSONObject(i);

                int id = recipeJsonObj.getInt("id");
                String name = recipeJsonObj.getString("name");
                int servings = recipeJsonObj.getInt("servings");
                String image = recipeJsonObj.getString("image");

                List<Ingredient> ingredientList = getIngredientListFromJson(recipeJsonObj);
                List<Step> stepList = getStepListFromJson(recipeJsonObj);

                Recipe recipe = new Recipe(
                        id, name, ingredientList, stepList, servings, image);
                recipeList.add(recipe);
            }
        }
        return recipeList;
    }

    public static List getIngredientListFromJson(JSONObject recipeJsonObj) throws JSONException {
        List ingredientList = new ArrayList<Ingredient>();
        JSONArray ingredientsJsonArr = recipeJsonObj.getJSONArray("ingredients");
        for (int i = 0; i < ingredientsJsonArr.length(); i++) {
            JSONObject ingredientJsonObj = ingredientsJsonArr.getJSONObject(i);
            Ingredient ingredient = new Ingredient(
                    ingredientJsonObj.getInt("quantity"),
                    ingredientJsonObj.getString("measure"),
                    ingredientJsonObj.getString("ingredient")
            );
            ingredientList.add(ingredient);
        }
        return ingredientList;
    }

    public static List getStepListFromJson(JSONObject recipeJsonObj) throws JSONException {
        List stepList = new ArrayList<Step>();
        JSONArray stepsJsonArr = recipeJsonObj.getJSONArray("steps");
        for (int i = 0; i < stepsJsonArr.length(); i++) {
            JSONObject stepJsonObj = stepsJsonArr.getJSONObject(i);
            Step step = new Step(
                    stepJsonObj.getInt("id"),
                    stepJsonObj.getString("shortDescription"),
                    stepJsonObj.getString("description"),
                    stepJsonObj.getString("videoURL"),
                    stepJsonObj.getString("thumbnailURL")
            );
            stepList.add(step);
        }
        return stepList;
    }
}
