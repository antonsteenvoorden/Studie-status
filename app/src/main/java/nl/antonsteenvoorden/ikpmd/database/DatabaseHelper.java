package nl.antonsteenvoorden.ikpmd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Anton on 01/12/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String dbName = "studiestatus";
    private static final int version = 1;
    private static DatabaseHelper ourInstance;
    public static SQLiteDatabase mSQLDB;

    public static synchronized DatabaseHelper getInstance(Context ctx) {
        if(ourInstance == null) {
            ourInstance = new DatabaseHelper(ctx);
            mSQLDB = ourInstance.getWritableDatabase();
        }
        return ourInstance;
    }

    private DatabaseHelper(Context ctx) {
        super(ctx, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Creating db","Creating database: studiestatus \nwith columns: " + ", "+ ", "+ ", "+ ", ");

        db.execSQL("CREATE TABLE " +
                DatabaseInfo.tableName + "(" +
                DatabaseInfo.columnName + " TEXT PRIMARY KEY, " +
                DatabaseInfo.columnECTS + " INTEGER," +
                DatabaseInfo.columnGrade + " INTEGER,"+
                DatabaseInfo.columnPeriod + " INTEGER" +
                ");"

        );
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
        super(context,name,factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseInfo.tableName);
        onCreate(db);
    }
    public void insert(String table, ContentValues contentValues) {
        mSQLDB.insert(table, null, contentValues);
    }
    public void update(String table, ContentValues values, String where) {
        mSQLDB.update(table,values, DatabaseInfo.columnName + " LIKE ?", new String[]{where});
    }
    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }

    //Overload
    public Cursor query(String table, String[] query) {
        return mSQLDB.query(table, query, null, null, null, null, null);
    }
}
