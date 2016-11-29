package wear.sunshine.android.example.com.capstone_1.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by jibin on 28/11/16.
 */

public class MedicineProvider extends ContentProvider {

    public static final String PROVIDER_NAME = "wear.sunshine.android.example.com.capstone_1.content.MedicineProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/medicine";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "_id";
    public static final String NAME = "title";
    public static final String COMPANY = "company";
    public static final String LOCAL_AGENT = "localagent";
    public static final String IMAGE_URL = "image";
    private static HashMap<String, String> MEDICINES_PROJECTION_MAP;

    static final int MEDICINE = 1;
    static final int MEDICINE_ID = 2;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "medicine", MEDICINE);
        uriMatcher.addURI(PROVIDER_NAME, "medicine/#", MEDICINE_ID);
    }

    /**
     * Database specific constant declarations
     */

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "med";
    static final String MEDICINE_TABLE_NAME = "medicine";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + MEDICINE_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME + " TEXT NOT NULL,  "
                    + COMPANY + " TEXT, " + LOCAL_AGENT + " TEXT ,  " + IMAGE_URL + " TEXT);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = dbHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(MEDICINE_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(MEDICINE_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case MEDICINE:
                qb.setProjectionMap(MEDICINES_PROJECTION_MAP);
                break;

            case MEDICINE_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortOrder == null || sortOrder.isEmpty()) {

            sortOrder = NAME;
        }

        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case MEDICINE:
                count = db.delete(MEDICINE_TABLE_NAME, selection, selectionArgs);
                break;

            case MEDICINE_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(MEDICINE_TABLE_NAME, _ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case MEDICINE:
                count = db.update(MEDICINE_TABLE_NAME, values, selection, selectionArgs);
                break;

            case MEDICINE_ID:
                count = db.update(MEDICINE_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {

            case MEDICINE:
                return "vnd.android.cursor.dir/vnd.capstone_1.medicine";

            case MEDICINE_ID:
                return "vnd.android.cursor.item/vnd.capstone_1.medicine";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
