package tw.tcnr.cos;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

////----------------------------------------------------------
//建構式參數說明：
//context
//可以操作資料庫的內容本文，一般可直接傳入Activity物件。
//name
//要操作資料庫名稱，如果資料庫不存在，會自動被建立出來並呼叫onCreate()方法。
//factory
//用來做深入查詢用，入門時用不到。
//version
//版本號碼。
////-----------------------
public class FriendDbHelper extends SQLiteOpenHelper {
    String TAG = "tcnr=>";
    public String sCreateTableCommand;    // 資料庫名稱
    private static final String DB_FILE = "friends.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;    // 資料表名稱
    private static final String DB_TABLE = "member";    // 資料庫物件，固定的欄位變數
    private static final String crTBsql = "CREATE TABLE " + DB_TABLE + " ( "
            + "id INTEGER PRIMARY KEY," + "name TEXT NOT NULL," + "grp TEXT,"
            + "address TEXT);";
    private static SQLiteDatabase database;

    //----------------------------------------------
    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen())
        {
            database = new FriendDbHelper(context, DB_FILE, null, VERSION)
                    .getWritableDatabase();
        }
        return database;
    }

    public FriendDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
        super(context, "friends.db", null, 1);
        sCreateTableCommand = "";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crTBsql);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade()");

        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        onCreate(db);
    }


    public String FindRec(String tname) {
        SQLiteDatabase db = getReadableDatabase();
        String fldSet = "ans=";
        String sql = "SELECT * FROM " + DB_TABLE +
                " WHERE name LIKE ? ORDER BY id ASC";
        String[] args = {"%" + tname + "%"};

        Cursor recSet = db.rawQuery(sql, args);

        int columnCount = recSet.getColumnCount();
        Log.d(TAG,"ans:"+recSet.getCount());
        if (recSet.getCount() != 0)
        {
            recSet.moveToFirst();
            fldSet = recSet.getString(0) + " "
                    +recSet.getString(1) + " "
                    +recSet.getString(2) + " "
                    +recSet.getString(3) + "\n";

            while (recSet.moveToNext())
            {
                for (int i = 0; i < columnCount; i++)
                {
                    fldSet += recSet.getString(i) + " ";
                }
                fldSet +="\n";
            }
        }
        recSet.close();
        db.close();
        return fldSet;
    }
}

