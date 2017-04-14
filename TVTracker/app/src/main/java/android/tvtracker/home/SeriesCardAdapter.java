package android.tvtracker.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.tvtracker.MainActivity;
import android.tvtracker.R;
import android.tvtracker.tools.ImageDownloader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SeriesCardAdapter extends RecyclerView.Adapter<SeriesCardAdapter.ViewHolder> {
    private Context mContext;
    private List<SeriesCardItem> mItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mThumbnailView;
        private TextView mTitleView;
        private TextView mDescriptionView;
        private SeriesCardItem mItem;
        private Toolbar mToolbar;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mThumbnailView = (ImageView) itemView.findViewById(R.id.series_card_thumbnail);
            this.mTitleView = (TextView) itemView.findViewById(R.id.series_card_title);
            this.mDescriptionView = (TextView) itemView.findViewById(R.id.series_card_description);
            this.mToolbar = (Toolbar) itemView.findViewById(R.id.series_card_toolbar);

            mToolbar.inflateMenu(R.menu.series_card);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.action_youtube) {

                        return true;
                    }
                    return false;
                }
            });
        }

        public SeriesCardItem getItem() {
            return mItem;
        }

        public void setItem(SeriesCardItem item) {
            new ImageDownloader(this.mThumbnailView).execute(item.getImageUrl());
            this.mTitleView.setText(item.getTitle());
            this.mDescriptionView.setText(item.getDescription());
            this.mItem = item;
        }
    }

    public SeriesCardAdapter(Context context, List<SeriesCardItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public SeriesCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.series_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SeriesCardAdapter.ViewHolder viewHolder, final int position) {
        final SeriesCardItem item = mItems.get(position);
        viewHolder.setItem(item);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on click action
            }
        });

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
