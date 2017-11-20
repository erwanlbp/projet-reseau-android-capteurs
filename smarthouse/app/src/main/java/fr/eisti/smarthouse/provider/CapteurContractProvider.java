package fr.eisti.smarthouse.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteurContractProvider {
    public static final String AUTHORITY = "fr.eisti.smarthouse";
    public static final String BASE_PATH = "capteurs";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + BASE_PATH;
    public static final String CONTENT_CAPTEUR_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + BASE_PATH;

    public static final class Capteurs implements BaseColumns {
        public static final String TABLE_NAME = "Capteurs";
        public static final String COLUMN_NAME = "capteur_name";
        public static final String COLUMN_TYPE = "capteur_type";
    }
}
