package cn.com.chengz.keepsecretcode.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/10/22.
 */
public class Account {
    int id;
    String accountName;
    List<User> userList;

    public Account() {

    }

    public Account(String accountName, List<User> userList) {
        this.accountName = accountName;
        this.userList = userList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
