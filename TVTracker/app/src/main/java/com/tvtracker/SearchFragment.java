package com.tvtracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.tvtracker.controllers.SearchController;
import com.tvtracker.interfaces.ISearchFragment;
import com.tvtracker.models.ListShow;
import com.tvtracker.tools.SeriesSearchSuggestion;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchFragment extends Fragment implements ISearchFragment {
    @BindView(R.id.floating_search_view) FloatingSearchView mSearchView;
    private Unbinder unbinder;
    private SearchController mController;
    private OnSearchFragmentInteractionListener mListener;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.fragment_search);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mController = new SearchController(this, getContext());
        mController.start();
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                if (newQuery.length() >= 3) {
                    mController.search(newQuery);
                }
                else {
                    mSearchView.clearSuggestions();
                }
            }
        });

      mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
          @Override
          public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
              SeriesSearchSuggestion suggestion = (SeriesSearchSuggestion) searchSuggestion;
              mListener.onSearchFragmentInteraction(suggestion.getId());
          }

          @Override
          public void onSearchAction(String currentQuery) {

          }
      });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchFragmentInteractionListener) {
            mListener = (OnSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateSuggestions(ListShow[] shows) {
        ArrayList<SeriesSearchSuggestion> list = new ArrayList<>();
        for(ListShow show : shows) {
            list.add(new SeriesSearchSuggestion(show.id, show.name));
        }
        mSearchView.swapSuggestions(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnSearchFragmentInteractionListener {
        void onSearchFragmentInteraction(int seriesId);
    }
}
