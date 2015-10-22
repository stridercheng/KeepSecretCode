package cn.com.chengz.keepsecretcode.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.com.chengz.keepsecretcode.R;
import cn.com.chengz.keepsecretcode.adapter.UserListAdapter;
import cn.com.chengz.keepsecretcode.db.DBManager;
import cn.com.chengz.keepsecretcode.entity.Account;
import cn.com.chengz.keepsecretcode.entity.User;
import cn.com.chengz.keepsecretcode.utils.DensityUtils;

public class AccountDetailActivity extends AppCompatActivity {
    @InjectView(R.id.accountname)
    TextView tv_accountName;
    @InjectView(R.id.users)
    SwipeMenuListView userListView;
    private int accountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        ButterKnife.inject(this);
        Intent intent = getIntent();
        accountId = intent.getIntExtra("accountId", 0);
        initData();
    }

    private void initData() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(DensityUtils.dp2px(AccountDetailActivity.this, 90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(DensityUtils.dp2px(AccountDetailActivity.this, 90));
                // set a icon
                deleteItem.setIcon(R.mipmap.icon_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        userListView.setMenuCreator(creator);
        userListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        DBManager dbManager = new DBManager(this);
        Account account = dbManager.getAccountById(accountId);
        List<User> userList = account.getUserList();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        tv_accountName.setText(account.getAccountName());
        UserListAdapter adapter = new UserListAdapter(this, userList);
        userListView.setAdapter(adapter);

        userListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
}
