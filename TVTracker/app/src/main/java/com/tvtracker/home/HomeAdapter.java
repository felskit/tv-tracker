package com.tvtracker.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tvtracker.HomeFragment;
import com.tvtracker.models.HomeEpisode;
import com.tvtracker.seriesDetails.EpisodesListFragment;
import com.tvtracker.tools.ImageDownloader;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context mContext;
    private List<HomeEpisode> mItems;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mThumbnailView;
        private TextView mTitleView;
        private TextView mDescriptionView;
        private HomeEpisode mItem;
        private Toolbar mToolbar;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.mThumbnailView = (ImageView) itemView.findViewById(com.tvtracker.R.id.series_card_thumbnail);
            this.mTitleView = (TextView) itemView.findViewById(com.tvtracker.R.id.series_card_title);
            this.mDescriptionView = (TextView) itemView.findViewById(com.tvtracker.R.id.series_card_description);
            this.mToolbar = (Toolbar) itemView.findViewById(com.tvtracker.R.id.series_card_toolbar);

            mToolbar.inflateMenu(com.tvtracker.R.menu.series_card);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == com.tvtracker.R.id.action_youtube) {
                        String title = mItem.name;
                        title = title.replace(' ', '+');
                        title = title.concat("+trailer+");
                        itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/results?search_query=".concat(title))));
                        return true;
                    }
                    if (id == com.tvtracker.R.id.action_seriesDetails) {
                        ((HomeFragment.OnHomeFragmentInteractionListener) itemView.getContext()).onFragmentInteraction(mItem);
                        return true;
                    }
                    return false;
                }
            });

            itemView.setOnClickListener(this);
        }

        public HomeEpisode getItem() {
            return mItem;
        }

        public void setItem(HomeEpisode item) {
            new ImageDownloader(this.mThumbnailView).execute(item.image);
            this.mTitleView.setText(item.name);
            this.mDescriptionView.setText(Html.fromHtml(item.summary));
            this.mItem = item;
        }

        @Override
        public void onClick(View v) {
            ((EpisodesListFragment.OnEpisodeInteractionListener) v.getContext()).onFragmentInteraction(mItem.episodeId);
        }
    }

    public HomeAdapter(Context context, List<HomeEpisode> items) {
        this.mContext = context;
        this.mItems = items;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(com.tvtracker.R.layout.series_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.ViewHolder viewHolder, final int position) {
        final HomeEpisode item = mItems.get(position);
        viewHolder.setItem(item);

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // on long click action
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
