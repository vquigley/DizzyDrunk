package com.grangewood.dizzydrunk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.Toast;

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

        ((Button)findViewById(R.id.addPlayer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditPlayer.class);
                startActivityForResult(intent, REQUEST_ADD_EDIT_PLAYER);
            }
        });

        ((FloatingActionButton)findViewById(R.id.addPlayerFab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayGame();
            }
        });

        UpdateView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
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
            UpdateList((Player) data.getParcelableExtra(Player.INTENT_EXTRA_ID));
        }
    }

    private void UpdateList(Player player) {
        list.add(0, player);
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

    public void showPopup(View v) {
        final int position = (int)v.getTag();

        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit_player:
                        return true;
                    case R.id.delete_player:
                        delete(position);
                        return true;
                    default:
                        return false;
                }
            }

        });

        MenuInflater inflater = popup.getMenuInflater();

        inflater.inflate(R.menu.menu_player_card, popup.getMenu());
        popup.show();
    }

    private void delete(int position) {

        PlayerDb db = new PlayerDb(this);
        db.deleteRecord(list.remove(position));

        mAdapter.notifyDataSetChanged();
        UpdateView();
    }

    private Player getPlayerInHierarchy(View v) {

        Object tag = null;
        ViewGroup currentView = (ViewGroup)v.getParent();
        while (currentView != null) {
            tag = currentView.getTag();

            if (tag instanceof Player)
                break;

            currentView = (ViewGroup)currentView.getParent();
        }

        return (Player)tag;
    }
}
