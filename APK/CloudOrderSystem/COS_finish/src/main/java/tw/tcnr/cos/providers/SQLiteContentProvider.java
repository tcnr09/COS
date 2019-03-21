package tw.tcnr.cos.providers;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class SQLiteContentProvider extends ContentProvider {
    private static final String AUTHORITY = "tw.tcnr.cos.providers3";
    private static String DB_FILE = "cos.db", DB_TABLE = "Products";
    private static final int URI_ROOT = 0, DB_TABLE_FRIENDS = 1;
    public static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DB_TABLE);
    private static UriMatcher sUriMatcher = new UriMatcher(URI_ROOT);

    static {
        sUriMatcher.addURI(AUTHORITY, DB_TABLE, DB_TABLE_FRIENDS);
    }

    private static SQLiteDatabase mFriendDb;
    static String TAG = "tcnr11=>";
    private static String crTBsql = "CREATE TABLE " + DB_TABLE + " ( "
            + "PID INTEGER PRIMARY KEY," + "Type TEXT NOT NULL," + "Productname TEXT,"
            + "L_Price INTEGER, " + "S_Price INTEGER,"+"Image TEXT);";

    public static void setSQLITE(String sqliteString, String tableName) {
        //
//        mFriendDb = friendDbOpenHelper.getWritableDatabase();
////        String aa = "a";
//        // 檢查資料表是否已經存在，如果不存在，就建立一個。
//        Cursor cursor = mFriendDb
//                .rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
//        if (cursor != null) {
//            if (cursor.getCount() == 0) // 沒有資料表，要建立一個資料表。
//                mFriendDb.execSQL(crTBsql);
//
//            cursor.close();
//
//        }

        crTBsql = sqliteString;
        DB_TABLE = tableName;
        try{
            mFriendDb.execSQL(crTBsql);
        }catch (Exception e){
            Log.d(TAG,"@@@@@@"+e.toString());
        }
    }

    public static void selectSQLite(String tablename){
        DB_TABLE = tablename;
    }

    @Override
    public boolean onCreate() {
        // ---宣告 使用Class DbOpenHelper.java 作為處理SQLite介面
        // Content Provider 就是 data Server, 負責儲存及提供資料, 他允許任何不同的APP使用
        // 共同的資料(不同的APP用同一個SQLite).

        DbOpenHelper friendDbOpenHelper = new DbOpenHelper(getContext(), DB_FILE, null, 1);

        mFriendDb = friendDbOpenHelper.getWritableDatabase();
//        String aa = "a";
        // 檢查資料表是否已經存在，如果不存在，就建立一個。
        Cursor cursor = mFriendDb
                .rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0) // 沒有資料表，要建立一個資料表。
                mFriendDb.execSQL(crTBsql);

            cursor.close();

        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (sUriMatcher.match(uri) != DB_TABLE_FRIENDS) {

            throw new IllegalArgumentException("Unknown URI " + uri);
        }
//  Cursor c = mFriendDb.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)


        // query 內引數 selection 是 選擇那些欄位
        Cursor c = mFriendDb.query(true, DB_TABLE, projection, selection, selectionArgs, null, null, sortOrder, null); //"ASC DESC"
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (sUriMatcher.match(uri) != DB_TABLE_FRIENDS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        long rowId = mFriendDb.insert(DB_TABLE, null, values);
        if (rowId > 0) {
            // 在已有的 Uri的後面追加ID數據
            Uri insertedRowUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            ;
            // 通知數據已經改變
            getContext().getContentResolver().notifyChange(insertedRowUri, null);
            return insertedRowUri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (sUriMatcher.match(uri) != DB_TABLE_FRIENDS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        int rowsAffected = mFriendDb.delete(DB_TABLE, selection, null);
        return rowsAffected;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (sUriMatcher.match(uri) != DB_TABLE_FRIENDS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        int rowsAffected = mFriendDb.update(DB_TABLE, values, selection, null);
        return rowsAffected;
    }
    // ------------------------

}