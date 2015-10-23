package cn.com.chengz.keepsecretcode.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by Administrator on 2015/10/22.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper mInstance;
    public synchronized static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }

        return mInstance;
    }

    private DBHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory().getPath() + "/codelost/code.db3", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String accountSQL = "create table if not exists account(id integer primary key, accountname text)";
        String userSQL = "create table if not exists user(id integer primary key, username text, password text, accountid integer)";
        String masterSQL = "create table if not exists master(password text)";
        db.execSQL(accountSQL);
        db.execSQL(userSQL);
        db.execSQL(masterSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
