package cn.com.chengz.keepsecretcode.entity;

/**
 * Created by Administrator on 2015/10/22.
 */
public class User {
    String userName;
    String passWord;

    public User() {

    }

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
