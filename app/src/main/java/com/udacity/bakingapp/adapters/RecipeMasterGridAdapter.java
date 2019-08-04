package com.udacity.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.Recipe;

import java.util.List;

public class RecipeMasterGridAdapter extends RecyclerView.Adapter<RecipeMasterGridAdapter.RecipeAdapterViewHolder> {

    private static final String TAG = RecipeMasterGridAdapter.class.getSimpleName();
    private List<Recipe> mRecipeData;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeMasterGridAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mRecipeName;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            mRecipeName = view.findViewById(R.id.recipe_name);
            mRecipeName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onRecipeClick(mRecipeData.get(adapterPosition));
        }
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.fragment_recipe_on_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeMasterGridAdapter.RecipeAdapterViewHolder recipeAdapterViewHolder, int position) {
        Recipe recipeSelected = mRecipeData.get(position);
        recipeAdapterViewHolder.mRecipeName.setText(recipeSelected.getName());
    }

    @Override
    public int getItemCount() {
        if (null == mRecipeData) return 0;
        return mRecipeData.size();
    }

    public void setRecipeData(List<Recipe> recipeData) {
        mRecipeData = recipeData;
        notifyDataSetChanged();
    }
}
