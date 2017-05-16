package com.tvtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.tvtracker.controllers.FavouritesGetController;
import com.tvtracker.controllers.FavouritesPostController;
import com.tvtracker.adapters.FavouriteAdapter;
import com.tvtracker.interfaces.IFavouritesGetFragment;
import com.tvtracker.interfaces.IPostFragment;
import com.tvtracker.models.ListShow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment implements IFavouritesGetFragment, IPostFragment {
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<ListShow> mItems;
    private LinearLayoutManager mLayoutManager;
    private boolean isSuggested = false;
    private FavouritesGetController mGetController;
    private FavouritesPostController mPostController;
    private FavouriteAdapter mAdapter;

    public FavouritesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSuggested = getArguments().getBoolean("isSuggested");
        mItems = new ArrayList<>();

        // TODO
        mGetController = new FavouritesGetController(this);
        mGetController.start();

        mPostController = new FavouritesPostController(this);
        mPostController.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites_list, container, false);
        getActivity().setTitle(isSuggested ? R.string.fragment_suggested : R.string.fragment_favourites);

        if (isSuggested)
            mGetController.getSuggested();
        else
            mGetController.getFavourites();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new FavouriteAdapter(mItems, mListener, isSuggested);
            recyclerView.setAdapter(mAdapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);

            if (!isSuggested) {
                ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int idx = viewHolder.getAdapterPosition();
                        mPostController.removeFavourite(mItems.get(idx).id);
                        mItems.remove(idx);
                        mAdapter.notifyItemRemoved(idx);
                        mAdapter.notifyItemRangeChanged(idx, mItems.size());
                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.recycle();
        mItems.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FavouritesFragment.OnListFragmentInteractionListener) {
            mListener = (FavouritesFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateList(ListShow[] shows) {
        mItems.clear();
        for (ListShow show : shows) {
            mItems.add(show);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void notify(String message) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ListShow item);
    }

    public boolean IsSuggestedFragment() {
        return isSuggested;
    }
}
