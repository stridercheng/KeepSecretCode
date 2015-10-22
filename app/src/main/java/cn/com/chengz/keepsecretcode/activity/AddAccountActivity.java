package cn.com.chengz.keepsecretcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.com.chengz.keepsecretcode.R;
import cn.com.chengz.keepsecretcode.db.DBManager;
import cn.com.chengz.keepsecretcode.entity.Account;
import cn.com.chengz.keepsecretcode.entity.User;

public class AddAccountActivity extends AppCompatActivity {
    @InjectView(R.id.accountname)
    EditText accountName;
    @InjectView(R.id.username)
    EditText userName;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.save)
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        ButterKnife.inject(this);

        initData();
    }

    private void initData() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAccount();
            }
        });
    }

    private void saveAccount() {
        DBManager dbManager;

        Account account = new Account();
        String accountname = accountName.getText().toString();
        if (accountname == null || "".equals(accountname)) {
            return;
        }
        account.setAccountName(accountname);
        dbManager = new DBManager(this);

        User user = new User();
        String username = userName.getText().toString();
        if (username == null || "".equals(username)) {
            dbManager.saveAccount(account);
        } else {
            user.setUserName(username);
            user.setPassWord(password.getText().toString());
            List<User> users = new ArrayList<>();
            users.add(user);
            account.setUserList(users);
            dbManager.saveAccount(account);
        }

        setResult(11);
        finish();
    }
}
