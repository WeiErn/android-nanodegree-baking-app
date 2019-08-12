package com.udacity.bakingapp.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.bakingapp.data.Ingredient;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class IngredientListConverter implements Serializable {
    Type type = new TypeToken<List<Ingredient>>() {
    }.getType();

    @TypeConverter
    public String fromIngredientList(List<Ingredient> ingredientList) {
        if (ingredientList == null) {
            return (null);
        }
        Gson gson = new Gson();
        String json = gson.toJson(ingredientList, type);
        return json;
    }

    @TypeConverter
    public List<Ingredient> toIngredientList(String ingredientListString) {
        if (ingredientListString == null) {
            return (null);
        }
        Gson gson = new Gson();
        List<Ingredient> ingredientList = gson.fromJson(ingredientListString, type);
        return ingredientList;
    }
}
