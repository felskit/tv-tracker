package com.tvtracker.adapters;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import com.tvtracker.FavouritesFragment.OnListFragmentInteractionListener;
import com.tvtracker.models.ListShow;
import com.tvtracker.tools.ImageDownloader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private final List<ListShow> mValues;
    private final OnListFragmentInteractionListener mListener;
    private boolean isSuggested = false;
    private Set<ViewHolder> mBoundViewHolders = new HashSet<>();

    public FavouriteAdapter(List<ListShow> items, OnListFragmentInteractionListener listener, boolean isSuggested) {
        mValues = items;
        mListener = listener;
        this.isSuggested = isSuggested;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.tvtracker.R.layout.fragment_favourites, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).name);
        new ImageDownloader(holder.mImageView).execute(mValues.get(position).image);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        mBoundViewHolders.add(holder);
    }

    public void recycle() {
        for (ViewHolder holder : mBoundViewHolders) {
            if(holder != null) {
                holder.recycle();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public ListShow mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(com.tvtracker.R.id.content);
            mImageView = (ImageView) view.findViewById(com.tvtracker.R.id.image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        public void recycle() {
            BitmapDrawable drawable = (BitmapDrawable)mImageView.getDrawable();
            if (drawable != null) {
                drawable.getBitmap().recycle();
            }
        }
    }
}
