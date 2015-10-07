package com.grangewood.dizzydrunk.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grangewood.dizzydrunk.R;
import com.grangewood.dizzydrunk.data.Player;
import com.grangewood.dizzydrunk.data.PlayerDataDbHelper;
import com.grangewood.dizzydrunk.data.PlayerDb;

import java.util.Random;

public class EditPlayer extends AppCompatActivity {

    Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);
        
        findViewById(R.id.savePlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlayer(v);
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ((ImageView)findViewById(R.id.actionImage)).setImageBitmap(imageBitmap);
            thumbnail = imageBitmap;
        }
    }

    public void savePlayer(View view) {

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        Player player = new Player();
        player.setColor(color); // Allow pick later!
        player.setName(((TextView) findViewById(R.id.name)).getText().toString());

        PlayerDb db = new PlayerDb(this);
        db.create(player);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(Player.INTENT_EXTRA_ID, player);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
