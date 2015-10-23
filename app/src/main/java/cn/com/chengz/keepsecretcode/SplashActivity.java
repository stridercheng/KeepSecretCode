package cn.com.chengz.keepsecretcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.com.chengz.keepsecretcode.db.DBManager;

public class SplashActivity extends AppCompatActivity {
    @InjectView(R.id.first)
    EditText et_first;
    @InjectView(R.id.confirm)
    EditText et_confirm;
    @InjectView(R.id.password)
    EditText et_passWord;
    @InjectView(R.id.login)
    Button login;
    @InjectView(R.id.enterCode)
    LinearLayout ll_enterCode;
    @InjectView(R.id.normalCode)
    LinearLayout ll_normalCode;

    boolean isFirst = true;
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        initData();
    }

    private void initData() {
        dbManager = new DBManager(this);
        String masterPassWord = dbManager.getMasterPassWord();
        if (masterPassWord == null || "".equals(masterPassWord)) {
            isFirst = true;
            ll_normalCode.setVisibility(View.GONE);
            ll_enterCode.setVisibility(View.VISIBLE);
        } else {
            isFirst = false;
            ll_normalCode.setVisibility(View.VISIBLE);
            ll_enterCode.setVisibility(View.GONE);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirst) {
                    saveMasterPassWord();
                } else {
                    checkAndLogin();
                }
            }
        });
    }

    private void checkAndLogin() {
        String passWord = et_passWord.getText().toString();
        String dbPassWord = dbManager.getMasterPassWord();
        if (!passWord.equals(dbPassWord)) {
            Toast.makeText(SplashActivity.this, "密码不正确，请重试。", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveMasterPassWord() {
        String first = et_first.getText().toString();
        if (first == null || "".equals(first)) {
            return;
        }

        String confirm = et_confirm.getText().toString();
        if (confirm == null || "".equals(confirm)) {
            Toast.makeText(SplashActivity.this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!first.equals(confirm)) {
            Toast.makeText(SplashActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        dbManager.saveMasterPassWord(first);
        isFirst = false;
        ll_normalCode.setVisibility(View.VISIBLE);
        ll_enterCode.setVisibility(View.GONE);
    }
}
