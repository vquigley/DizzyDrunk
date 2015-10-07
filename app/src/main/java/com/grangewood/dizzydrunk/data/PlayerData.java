package com.grangewood.dizzydrunk.data;

import android.provider.BaseColumns;

/**
 * Created by vquig on 07/10/15.
 */
public abstract class PlayerData implements BaseColumns {
    public static final String TABLE_NAME = "Player";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE_LOCATION = "imageLocation";
    public static final String COLUMN_COLOR = "color";

}
