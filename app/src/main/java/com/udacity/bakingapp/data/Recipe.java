package com.udacity.bakingapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.udacity.bakingapp.database.IngredientListConverter;
import com.udacity.bakingapp.database.StepListConverter;

import java.util.List;

@Entity(tableName = "recipe")
public class Recipe implements Parcelable {

    @PrimaryKey
    private int id;
    private String name;
    @TypeConverters(IngredientListConverter.class)
    @ColumnInfo(name="ingredient_list")
    private List<Ingredient> ingredientList;
    @TypeConverters(StepListConverter.class)
    @ColumnInfo(name = "step_list")
    private List<Step> stepList;
    private int servings;
    private String image;

    public int getId() { return id; }
    public String getName() { return name; }
    public List<Ingredient> getIngredientList() { return ingredientList; }
    public List<Step> getStepList() { return stepList; }
    public int getServings() { return servings; }
    public String getImage() { return image; }

    public Recipe(int id, String name, List ingredientList, List stepList, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientList = ingredientList;
        this.stepList = stepList;
        this.servings = servings;
        this.image = image;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientList = in.createTypedArrayList(Ingredient.CREATOR);
        stepList = in.createTypedArrayList(Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredientList);
        dest.writeTypedList(stepList);
        dest.writeInt(servings);
        dest.writeString(image);
    }
}
