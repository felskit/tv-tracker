package com.tvtracker.seriesDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tvtracker.controllers.EpisodesPostController;
import com.tvtracker.models.ShowEpisode;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
    private final List<ShowEpisode> mValues;
    private EpisodesPostController mEpisodesController;

    public EpisodeAdapter(List<ShowEpisode> items, EpisodesPostController controller) {
        mValues = items;
        mEpisodesController = controller;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.tvtracker.R.layout.fragment_episode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).name);
        holder.mSeenView.setChecked(mValues.get(position).watched);

        LinearLayout layout = (LinearLayout) holder.itemView.findViewById(com.tvtracker.R.id.episodeLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mSeenView.setChecked(!holder.mSeenView.isChecked());
                mEpisodesController.setWatched(holder.mItem.id, holder.mSeenView.isChecked(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public final View mView;
        public final CheckBox mSeenView;
        public final TextView mTitleView;
        public ShowEpisode mItem;

        private Context mContext;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(com.tvtracker.R.id.title);
            mSeenView = (CheckBox) view.findViewById(com.tvtracker.R.id.checkbox);
            mContext = view.getContext();
            view.setOnLongClickListener(this);
        }

        @Override
        public String toString() {
            return mItem.name;
        }

        @Override
        public boolean onLongClick(View v) {
            ((EpisodesListFragment.OnEpisodeInteractionListener) v.getContext()).onFragmentInteraction(mItem.id);
            return true;
        }
    }
}
