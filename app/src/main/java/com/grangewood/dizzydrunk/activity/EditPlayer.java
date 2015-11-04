package com.grangewood.dizzydrunk.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grangewood.dizzydrunk.R;
import com.grangewood.dizzydrunk.data.Player;
import com.grangewood.dizzydrunk.data.PlayerDataDbHelper;
import com.grangewood.dizzydrunk.data.PlayerDb;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class EditPlayer extends AppCompatActivity {

    int selectedColor;

    File destination;

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

        Random rnd = new Random();
        selectedColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        View colorView = findViewById(R.id.playerColor);
        ((GradientDrawable)colorView.getBackground()).setColor(selectedColor);
    }

    private String createImageName() {
        // Create a«n image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        return imageFileName;
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

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                destination = new   File(Environment.getExternalStorageDirectory(), createImageName());

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


            ImageView image = ((ImageView)findViewById(R.id.actionImage));

            image.setImageBitmap(Player.getBitmap(destination.getAbsolutePath()));

            findViewById(R.id.selfieText).setVisibility(View.GONE);

            galleryAddPic();
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(destination.getAbsolutePath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public String getRealPathFromURI(Uri contentUri)
    {
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        catch (Exception e)
        {
            return contentUri.getPath();
        }
    }

    public void savePlayer(View view) {

        if (validateInput() == false)
            return;

        Player player = new Player();
        player.setColor(selectedColor);
        player.setName(((TextView) findViewById(R.id.name)).getText().toString());
        player.setImageLocation(destination.getAbsolutePath());

        PlayerDb db = new PlayerDb(this);
        db.create(player);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(Player.INTENT_EXTRA_ID, player);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private boolean validateInput() {
        EditText nameView = (EditText)findViewById(R.id.name);

        if (nameView.getText().toString().replace(" ", "").equals("")) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
