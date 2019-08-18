package com.udacity.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.StepActivity;
import com.udacity.bakingapp.adapters.StepMasterListAdapter;
import com.udacity.bakingapp.data.Step;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class StepMasterListFragment extends Fragment implements
        StepMasterListAdapter.StepAdapterOnClickHandler {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private StepMasterListAdapter mStepMasterListAdapter;
    private RecyclerView mStepRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepMasterListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StepMasterListFragment newInstance(int columnCount) {
        StepMasterListFragment fragment = new StepMasterListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_master_list, container, false);
        mStepRecyclerView = view.findViewById(R.id.list_steps);

        // Set the adapter
        if (mStepRecyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                mStepRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mStepRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mStepRecyclerView.setHasFixedSize(true);
            mStepMasterListAdapter = new StepMasterListAdapter(this);
            mStepRecyclerView.setAdapter(mStepMasterListAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof StepAdapterOnClickHandler) {
//            mClickHandler = (StepAdapterOnClickHandler) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mClickHandler = null;
    }

    @Override
    public void onStepClick(Step step, String recipeName) {
        Context context = getActivity();
        Class destinationClass = StepActivity.class;
        Intent intentToStartStepActivity = new Intent(context, destinationClass);
        intentToStartStepActivity.putExtra(getString(R.string.intent_extra_step), step);
        intentToStartStepActivity.putExtra(getString(R.string.recipe_name), recipeName);
        startActivity(intentToStartStepActivity);
    }

    public void setStepDataAndRecipeNameInAdapter(List<Step> stepData, String recipeName) {
        mStepMasterListAdapter.setStepData(stepData);
        mStepMasterListAdapter.setRecipeName(recipeName);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface StepAdapterOnClickHandler {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(Step step);
//    }
}
