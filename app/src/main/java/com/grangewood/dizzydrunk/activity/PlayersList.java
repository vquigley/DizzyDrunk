package com.grangewood.dizzydrunk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.grangewood.dizzydrunk.data.PlayerDb;
import com.grangewood.dizzydrunk.data.PlayersAdaptor;
import com.grangewood.dizzydrunk.R;
import com.grangewood.dizzydrunk.data.Player;

import java.util.ArrayList;

public class PlayersList extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Player> list;
    
    private static final int REQUEST_ADD_EDIT_PLAYER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.playerList);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        PlayerDb db = new PlayerDb(this);
        list = db.read();

        mAdapter = new PlayersAdaptor(list);
        mRecyclerView.setAdapter(mAdapter);

        ((FloatingActionButton)findViewById(R.id.addPlayerFab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditPlayer.class);
                startActivityForResult(intent, REQUEST_ADD_EDIT_PLAYER);
            }
        });

        UpdateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_players, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.playGame) {
            PlayGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_EDIT_PLAYER && resultCode == RESULT_OK) {
            UpdateList((Player)data.getParcelableExtra(Player.INTENT_EXTRA_ID));
        }
    }

    private void UpdateList(Player player) {
        list.add(player);
        UpdateView();
        mAdapter.notifyDataSetChanged();
    }

    private void UpdateView() {
        if (list.size() > 0) {
            findViewById(R.id.playerList).setVisibility(View.VISIBLE);
            findViewById(R.id.NoPlayers).setVisibility(View.GONE);
        }
        else {
            findViewById(R.id.playerList).setVisibility(View.GONE);
            findViewById(R.id.NoPlayers).setVisibility(View.VISIBLE);
        }
    }

    private void PlayGame() {
        Intent intent = new Intent(this, PlayGame.class);
        intent.putExtra(Player.INTENT_EXTRA_ID, list);
        startActivity(intent);
    }
}
