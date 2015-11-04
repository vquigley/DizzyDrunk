package com.grangewood.dizzydrunk.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.media.ThumbnailUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grangewood.dizzydrunk.R;

import java.util.ArrayList;

/**
 * Created by vquig on 05/10/15.
 */
public class PlayersAdaptor extends RecyclerView.Adapter<PlayersAdaptor.ViewHolder> {

    private ArrayList<Player> playerList;

    public PlayersAdaptor(ArrayList<Player> list) {
        playerList = list;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;
        public CardView cardView;
        public View playerColor;
        public ImageView menuView;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textView = (TextView) itemLayoutView.findViewById(R.id.actionText);
            imageView = (ImageView) itemLayoutView.findViewById(R.id.actionImage);
            cardView = (CardView) itemLayoutView.findViewById(R.id.card_view);
            playerColor = itemLayoutView.findViewById(R.id.playerColor);
            menuView = (ImageView)itemLayoutView.findViewById(R.id.popup_icon);
        }
    }

    @Override
    public PlayersAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_player, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player p = playerList.get(position);
        holder.textView.setText(p.getName());
        ((GradientDrawable)holder.playerColor.getBackground()).setColor(p.getColor());

        holder.imageView.setImageBitmap(p.getBitmap());

        holder.menuView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return playerList.size();
    }
}
