package com.nunnaguppala.suryaharsha.cnpokerclub.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nunnaguppala.suryaharsha.cnpokerclub.R;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.UserEntity;

import java.util.List;

public class CashierAdapter extends ArrayAdapter<UserEntity> {

    private final LayoutInflater inflater;

    public CashierAdapter(Context context) {
        super(context, R.layout.user_selector_list_item);
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<UserEntity> users, boolean clear) {
        if (clear) {
            clear();
        }
        if (users != null) {
            addAll(users);
        }
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view;
        if(convertView == null){
            view = inflater.inflate(R.layout.user_selector_list_item, parent, false);
            CashierAdapter.ViewHolder viewHolder = new CashierAdapter.ViewHolder((TextView) view.findViewById(R.id.cashier_name_item));
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
        }

        CashierAdapter.ViewHolder holder = (CashierAdapter.ViewHolder) view.getTag();
        TextView textView = holder.textView;

        UserEntity user = getItem(position);
        SpannableStringBuilder builder = new SpannableStringBuilder(user.getFirstName());
        builder.setSpan(new TextAppearanceSpan(getContext(), android.R.style.TextAppearance_DeviceDefault_Large),
                0, user.getFirstName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder, TextView.BufferType.SPANNABLE);
        return view;
    }

    private static final class ViewHolder {

        ViewHolder(TextView textView) {
            super();
            this.textView = textView;
        }

        TextView textView;
    }
}
