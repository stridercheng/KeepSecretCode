package cn.com.chengz.keepsecretcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.com.chengz.keepsecretcode.R;
import cn.com.chengz.keepsecretcode.db.DBManager;
import cn.com.chengz.keepsecretcode.entity.User;

public class ModifyUserActivity extends AppCompatActivity {
    @InjectView(R.id.accountname)
    TextView tv_accountName;
    @InjectView(R.id.username)
    EditText et_userName;
    @InjectView(R.id.password)
    EditText et_passWord;
    @InjectView(R.id.save)
    Button save;
    private String accountName, userName, passWord;
    private int accountId, position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        accountId = intent.getIntExtra("accountId", 0);
        userName = intent.getStringExtra("userName");
        passWord = intent.getStringExtra("passWord");
        accountName = intent.getStringExtra("accountName");
        position = intent.getIntExtra("position", 0);

        initData();
    }

    private void initData() {
        tv_accountName.setText(accountName);
        et_userName.setText(userName);
        et_passWord.setText(passWord);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSave();
            }
        });
    }

    private void checkAndSave() {
        String userNameNew = et_userName.getText().toString();
        if (userNameNew == null || "".equals(userNameNew)) {
            Toast.makeText(ModifyUserActivity.this, "请输入正确的用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        String passWordNew = et_passWord.getText().toString();
        if (passWordNew == null || "".equals(passWordNew)) {
            Toast.makeText(ModifyUserActivity.this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User();
        user.setUserName(userNameNew);
        user.setPassWord(passWordNew);
        DBManager dbManager = new DBManager(this);
        dbManager.modifyUser(user, accountId, userName);

        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("userName", userNameNew);
        intent.putExtra("passWord", passWordNew);
        setResult(12, intent);

        finish();
    }
}
