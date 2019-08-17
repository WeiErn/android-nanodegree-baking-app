package com.udacity.bakingapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.Ingredient;

import java.util.List;

public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    Context mContext;
    int mLayoutId;
    List<Ingredient> mIngredientList;

    public IngredientListAdapter(Context context, int layoutId, List<Ingredient> ingredientList) {
        super(context, layoutId, ingredientList);
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mIngredientList = ingredientList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutId, parent, false);
        }

        Ingredient ingredient = mIngredientList.get(position);
        TextView ingredientTextView = convertView.findViewById(R.id.ingredient_item);
        ingredientTextView.setText(ingredient.getQuantity() + " " + ingredient.getMeasurement() + " " + ingredient.getIngredient());

        return convertView;
    }
}
