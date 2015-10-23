package cn.com.chengz.keepsecretcode.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.com.chengz.keepsecretcode.entity.Account;
import cn.com.chengz.keepsecretcode.entity.User;

/**
 * Created by Administrator on 2015/10/22.
 */
public class DBManager {
    private DBHelper dbHelper;

    public DBManager(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    public List<Account> getAllAccount() {
        List<Account> accountList = new ArrayList<>();
        String exesql = "select id, accountname from account ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(exesql, new String[]{});
        while (cursor.moveToNext()) {
            List<User> userList = new ArrayList<>();
            Account account = new Account();
            String exesql2 = " select * from user where accountid = ?";
            Cursor cursorUser = db.rawQuery(exesql2, new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex("id")))});
            while (cursorUser.moveToNext()) {
                User user = new User();
                user.setUserName(cursorUser.getString(cursorUser.getColumnIndex("username")));
                user.setPassWord(cursorUser.getString(cursorUser.getColumnIndex("password")));

                userList.add(user);
            }

            account.setId(cursor.getInt(cursor.getColumnIndex("id")));
            account.setAccountName(cursor.getString(cursor.getColumnIndex("accountname")));
            account.setUserList(userList);
            accountList.add(account);
            cursorUser.close();
        }

        cursor.close();
        db.close();
        return accountList;
    }

    public Account getAccountById(int id) {
        List<User> userList = new ArrayList<>();
        Account account = new Account();
        String exesql = " select * from account where id = ? ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(exesql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            account.setAccountName(cursor.getString(cursor.getColumnIndex("accountname")));
            account.setId(cursor.getInt(cursor.getColumnIndex("id")));
            String exeql2 = "select * from user where accountid = ?";
            Cursor cursorUser = db.rawQuery(exeql2, new String[]{String.valueOf(id)});
            while (cursorUser.moveToNext()) {
                User user = new User();
                user.setUserName(cursorUser.getString(cursorUser.getColumnIndex("username")));
                user.setPassWord(cursorUser.getString(cursorUser.getColumnIndex("password")));

                userList.add(user);
            }

            account.setUserList(userList);
            cursorUser.close();
        }

        cursor.close();
        db.close();
        return account;
    }

    public void saveAccount(Account account) {
        String accountname = account.getAccountName();
        List<User> userList = account.getUserList();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String insert = "insert into account(accountname) values('" + accountname + "')";
        db.execSQL(insert);

        String query = "select id from account where accountname = '" + accountname + "'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        int id = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            String insertUser = "insert into user(username, password, accountid) values('"
                    + user.getUserName() + "','" + user.getPassWord() + "','" + String.valueOf(id) + "')";
            db.execSQL(insertUser);
        }

        cursor.close();
        db.close();
    }

    public void addUser(User user, int accountid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String insert = "insert into user(username, password,accountid) values ('" + user.getUserName() +
                "','" + user.getPassWord() + "','" + accountid + "')";
        db.execSQL(insert);

        db.close();
    }

    public void deleteUser(User user, int accountid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String delete = "delete from user where accountid = '" + accountid + "' and username = '" + user.getUserName() +"'";
        db.execSQL(delete);
        db.close();
    }

    public void modifyUser(User user, int accountId, String userNameOld) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String modify = "update user set username = '" + user.getUserName() + "', password='" + user.getPassWord() +
                "' where username = '" + userNameOld + "' and accountId = '" + accountId + "'";
        db.execSQL(modify);
        db.close();
    }

    public void deleteAccount(int accountId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String delete = "delete from account where id = '" + accountId + "'";
        db.execSQL(delete);
        db.close();
    }
}
