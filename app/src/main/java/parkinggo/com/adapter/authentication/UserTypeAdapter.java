/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 12/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.adapter.authentication;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;
import parkinggo.com.R;
import parkinggo.com.data.model.Option;
import parkinggo.com.data.model.UserConf;


public class UserTypeAdapter extends ArrayAdapter<UserConf> {
    private Context context;

    public UserTypeAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull RealmResults<UserConf> userConfs) {
        super(context, resource, userConfs);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserConf userConf = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_multiple_choice, null);
            viewHolder = new ViewHolder();
            viewHolder.txtOption = (TextView) convertView.findViewById(R.id.text_option);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtOption.setText(userConf.getType());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_drop_down_view, null);
        TextView label = (TextView) view.findViewById(R.id.text_drop);
        label.setText(this.getItem(position).getType());
        return view;
    }

    private static class ViewHolder {
        private TextView txtOption;
    }
}
