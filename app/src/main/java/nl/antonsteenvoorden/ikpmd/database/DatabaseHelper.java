package nl.antonsteenvoorden.ikpmd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

import nl.antonsteenvoorden.ikpmd.model.Module;

/**
 * Created by Anton on 01/12/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String dbName = "studiestatus";
    private static final int version = 2;
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
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseInfo.columnName + " TEXT," +
                DatabaseInfo.columnECTS + " INTEGER," +
                DatabaseInfo.columnGrade + " INTEGER,"+
                DatabaseInfo.columnPeriod + " INTEGER" +
                ");"

        );

    }
    public void insertFromJson(ArrayList<Module> modules) {
        Log.d("DBHELPER", "InsertFromJson Called with modules size:" + modules.size());
        for(Module module : modules) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfo.columnName, module.getName());
            contentValues.put(DatabaseInfo.columnECTS, module.getEcts());
            contentValues.put(DatabaseInfo.columnGrade, module.getGrade());
            contentValues.put(DatabaseInfo.columnPeriod, module.getPeriod());
            Log.d("DBHELPER","Inserting" + module.toString());
            insert(DatabaseInfo.tableName, contentValues);
        }
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
    public void update(String table, ContentValues values, int where) {
        mSQLDB.update(table,values,BaseColumns._ID + " LIKE ?",new String[]{String.valueOf(where)});
    }
    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }

    //Overload
    public Cursor query(String table, String[] query) {
        return mSQLDB.query(table, query, null, null, null, null, null);
    }
}
