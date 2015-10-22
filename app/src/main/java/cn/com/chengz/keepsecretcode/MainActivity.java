package cn.com.chengz.keepsecretcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.com.chengz.keepsecretcode.activity.AccountDetailActivity;
import cn.com.chengz.keepsecretcode.activity.AddAccountActivity;
import cn.com.chengz.keepsecretcode.adapter.AccountListAdapter;
import cn.com.chengz.keepsecretcode.db.DBManager;
import cn.com.chengz.keepsecretcode.entity.Account;

public class MainActivity extends AppCompatActivity implements AccountListAdapter.OnAccountItemClickListener {
    private final static int REQUESTCODE = 11;
    @InjectView(R.id.secretList)
    RecyclerView secretList;
    @InjectView(R.id.fab)
    FloatingActionButton btn_add;
    private List<Account> accountList = new ArrayList<>();
    private DBManager dbManager;
    private  AccountListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        secretList.setLayoutManager(linearLayoutManager);
        secretList.setHasFixedSize(true);
        adapter = new AccountListAdapter(this, accountList);
        adapter.setOnAccountClickListener(this);
        secretList.setAdapter(adapter);

        dbManager = new DBManager(this);
        new GetAccountAsync(this).execute();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddAccountActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE) {
            new GetAccountAsync(this).execute();
        }
    }

    class GetAccountAsync extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        Context context;
        public GetAccountAsync(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("加在数据中，请稍后");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            accountList = dbManager.getAllAccount();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.setAccountList(accountList);
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onAccountClick(View v, int position) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AccountDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUserAccountClick(View v, int accountPosition, int userPosition) {
        this.onAccountClick(v, accountPosition);
    }
}
