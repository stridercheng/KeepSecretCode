package cn.com.chengz.keepsecretcode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.chengz.keepsecretcode.R;
import cn.com.chengz.keepsecretcode.entity.Account;
import cn.com.chengz.keepsecretcode.entity.User;

/**
 * Created by Administrator on 2015/10/22.
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private OnAccountItemClickListener mListener;
    private List<Account> accountList;
    public AccountListAdapter(Context context, List<Account> accountList) {
        this.mContext = context;
        this.accountList = accountList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_account_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(this);
        return viewHolder;
    }

    public void setOnAccountClickListener(OnAccountItemClickListener listener) {
        this.mListener = listener;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Account account = accountList.get(position);
        List<User> userList = account.getUserList();
        holder.itemView.setTag(position);
        holder.accountName.setText(account.getAccountName());
        holder.accountName.setTag(position);
        holder.users.removeAllViews();
        for (int i = 0; i < userList.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setText(userList.get(i).getUserName());
            textView.setTextSize(20);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
            textView.setLayoutParams(params);
            textView.setTag(R.id.accountid, position);
            textView.setTag(R.id.userid, i);
            holder.users.addView(textView);
        }
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    @Override
    public void onClick(View v) {
        mListener.onAccountClick(v, (Integer) v.getTag());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView accountName;
        private LinearLayout users;
        private View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            accountName = (TextView) itemView.findViewById(R.id.accountname);
            users = (LinearLayout) itemView.findViewById(R.id.users);
        }
    }

    public interface OnAccountItemClickListener{
        void onAccountClick(View v, int position);
    }
}
