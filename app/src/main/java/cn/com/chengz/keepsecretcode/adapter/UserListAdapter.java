package cn.com.chengz.keepsecretcode.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.com.chengz.keepsecretcode.R;
import cn.com.chengz.keepsecretcode.entity.User;

/**
 * Created by Administrator on 2015/10/22.
 */
public class UserListAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> userList;

    public UserListAdapter(Context context, List<User> userList) {
        this.mContext = context;
        this.userList = userList;
    }

    public void modifyData(User user, int position) {
        this.userList.remove(position);
        this.userList.add(position, user);
        this.notifyDataSetChanged();
    }

    public void deleteData(int position) {
        this.userList.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = userList.get(position);
        viewHolder.userName.setText(user.getUserName());
        viewHolder.passWord.setText(user.getPassWord());
        return convertView;
    }

    class ViewHolder{
        TextView userName, passWord;
        public ViewHolder(View view) {
            userName = (TextView) view.findViewById(R.id.username);
            passWord = (TextView) view.findViewById(R.id.password);
        }
    }
}
