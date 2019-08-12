package com.udacity.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.udacity.bakingapp.data.Recipe;

@Dao
public interface RecipeListDao {

    @Query("SELECT * FROM bakingapp ORDER BY id")
    LiveData<List<Recipe>> loadAllRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Query("SELECT * FROM bakingapp WHERE id = :id")
    LiveData<Recipe> loadRecipeById(int id);
}
