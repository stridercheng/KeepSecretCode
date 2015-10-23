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

public class AddUserActivity extends AppCompatActivity {
    @InjectView(R.id.accountname)
    TextView tv_accountName;
    @InjectView(R.id.username)
    EditText et_userName;
    @InjectView(R.id.password)
    EditText et_passWord;
    @InjectView(R.id.save)
    Button btn_save;

    private String accountName;
    private int accountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        accountName = intent.getStringExtra("accountName");
        accountId = intent.getIntExtra("accountId", 0);
        initData();
    }

    private void initData() {
        tv_accountName.setText(accountName);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSave();
            }
        });
    }

    private void checkAndSave() {
        String userNameNew = et_userName.getText().toString();
        if (userNameNew == null || "".equals(userNameNew)) {
            Toast.makeText(AddUserActivity.this, "请输入正确的用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        String passWordNew = et_passWord.getText().toString();
        if (passWordNew == null || "".equals(passWordNew)) {
            Toast.makeText(AddUserActivity.this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User();
        user.setUserName(userNameNew);
        user.setPassWord(passWordNew);
        DBManager dbManager = new DBManager(this);

        dbManager.addUser(user, accountId);
        Intent intent = new Intent();
        intent.putExtra("userName", userNameNew);
        intent.putExtra("passWord", passWordNew);
        setResult(13, intent);

        finish();
    }
}
