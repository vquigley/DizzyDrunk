package com.grangewood.dizzydrunk.data;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Shader;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vquig on 05/10/15.
 */
public class Player implements Parcelable {
    public static final String INTENT_EXTRA_ID = "PLAYER_INTENT_EXTRA";

    public Player(long id, String imageLocation, int color, String name) {
        this.id = id;
        this.imageLocation = imageLocation;
        this.color = color;
        this.name = name;
    }

    private long id;
    private String imageLocation;
    private int color;
    private String name;

    //  Required for google code, refactor at some point soon.
    public float mStartAngle;
    public float mEndAngle;
    public float mValue = 1;
    public Shader mShader;

    public Player() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getImageLocation());
        dest.writeString(getName());
        dest.writeInt(getColor());

    }

    public Player(Parcel in) {
        setId(in.readLong());
        setImageLocation(in.readString());
        setName(in.readString());
        setColor(in.readInt());
    }

    public static final Parcelable.Creator<Player> CREATOR
            = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public int getHighlight(float strength) {
        return Color.argb(
                0xff,
                Math.min((int) (strength * (float) Color.red(color)), 0xff),
                Math.min((int) (strength * (float) Color.green(color)), 0xff),
                Math.min((int) (strength * (float) Color.blue(color)), 0xff));
    }
}
