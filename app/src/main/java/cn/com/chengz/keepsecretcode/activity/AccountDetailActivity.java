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
    private final static int REQUESTCODE = 12;
    @InjectView(R.id.accountname)
    TextView tv_accountName;
    @InjectView(R.id.users)
    SwipeMenuListView userListView;
    private int accountId;
    private List<User> userList;
    DBManager dbManager;
    private UserListAdapter adapter;
    private Account account;
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
        dbManager = new DBManager(this);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(R.color.lightblue);
                // set item width
                openItem.setWidth(DensityUtils.dp2px(AccountDetailActivity.this, 90));
                // set item title
//                openItem.setTitle("Open");
                openItem.setIcon(R.mipmap.icon_modify);
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

        account = dbManager.getAccountById(accountId);
        userList = account.getUserList();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        tv_accountName.setText(account.getAccountName());
        adapter = new UserListAdapter(this, userList);
        userListView.setAdapter(adapter);

        userListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // modify
                        modifyUser(position);
                        break;
                    case 1:
                        // delete
                        deleteUser(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    private void modifyUser(int position) {
        User user = userList.get(position);
        Intent intent = new Intent();
        intent.setClass(AccountDetailActivity.this, ModifyUserActivity.class);
        intent.putExtra("accountId", accountId);
        intent.putExtra("accountName", account.getAccountName());
        intent.putExtra("userName", user.getUserName());
        intent.putExtra("passWord", user.getPassWord());
        intent.putExtra("position", position);
        startActivityForResult(intent, REQUESTCODE);
    }

    private void deleteUser(int position) {
        User user = userList.get(position);
        userList.remove(user);
        adapter.setUserList(userList);
        adapter.notifyDataSetChanged();
        dbManager.deleteUser(user, accountId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
            int position = data.getIntExtra("position", 0);
            User user = userList.get(position);
            user.setUserName(data.getStringExtra("userName"));
            user.setPassWord(data.getStringExtra("passWord"));
            userList.remove(position);
            userList.add(position, user);

            adapter.setUserList(userList);
            adapter.notifyDataSetChanged();
        }
    }
}
