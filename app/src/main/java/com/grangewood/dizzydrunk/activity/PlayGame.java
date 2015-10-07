package com.grangewood.dizzydrunk.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.grangewood.dizzydrunk.R;
import com.grangewood.dizzydrunk.view.WheelView;
import com.grangewood.dizzydrunk.data.Player;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PlayGame extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_game);

        ArrayList<Player> list = getIntent().getParcelableArrayListExtra(Player.INTENT_EXTRA_ID);

        final WheelView pie = (WheelView) this.findViewById(R.id.Pie);
        pie.setData(list);
    }
}
