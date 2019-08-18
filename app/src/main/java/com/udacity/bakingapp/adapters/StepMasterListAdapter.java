package com.udacity.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.Ingredient;
import com.udacity.bakingapp.data.Step;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Step} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class StepMasterListAdapter extends RecyclerView.Adapter<StepMasterListAdapter.StepAdapterViewHolder> {

    private String mRecipeName;
    private List<Step> mStepData;
    private final StepAdapterOnClickHandler mClickHandler;

    public interface StepAdapterOnClickHandler {
        void onStepClick(Step step, String recipeName);
    }

    public StepMasterListAdapter(StepAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mStepName;

        public StepAdapterViewHolder(View view) {
            super(view);
            mView = view;
            mStepName = view.findViewById(R.id.step_name);
        }
    }

    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_step_on_list, parent, false);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StepMasterListAdapter.StepAdapterViewHolder stepAdapterViewHolder, int position) {
        final Step stepSelected = mStepData.get(position);
        stepAdapterViewHolder.mStepName.setText(stepSelected.getShortDescription());

        stepAdapterViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mClickHandler) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mClickHandler.onStepClick(stepSelected, mRecipeName);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mStepData) return 0;
        return mStepData.size();
    }

    public void setStepData(List<Step> stepData) {
        mStepData = stepData;
        notifyDataSetChanged();
    }

    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
        notifyDataSetChanged();
    }
}
