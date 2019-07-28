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
import com.udacity.bakingapp.fragments.RecipeMasterGridFragment;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>
    implements RecipeMasterGridFragment.OnRecipeSelectedListener {

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private List<Recipe> mRecipeData;
    private final RecipeAdapterOnClickHandler mClickHandler;
    private Context mContext;

    @Override
    public void onRecipeSelected(Recipe recipe) {

    }

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
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
            mClickHandler.onClick(mRecipeData.get(adapterPosition));
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
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeAdapterViewHolder recipeAdapterViewHolder, int position) {
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
