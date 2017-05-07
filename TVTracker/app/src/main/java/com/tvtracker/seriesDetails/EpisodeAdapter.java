package com.tvtracker.seriesDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
    private final List<EpisodeItem> mValues;

    public EpisodeAdapter(List<EpisodeItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.tvtracker.R.layout.fragment_episode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).title);
        holder.mSeenView.setChecked(mValues.get(position).seen);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mSeenView.setChecked(!holder.mSeenView.isChecked());
            }
        };
        LinearLayout layout = (LinearLayout) holder.itemView.findViewById(com.tvtracker.R.id.episodeLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mSeenView.setChecked(!holder.mSeenView.isChecked());
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
        public EpisodeItem mItem;

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
            return mItem.title;
        }

        @Override
        public boolean onLongClick(View v) {
            ((EpisodesListFragment.OnEpisodeInteractionListener) v.getContext()).onFragmentInteraction(0);
            return true;
        }
    }
}
