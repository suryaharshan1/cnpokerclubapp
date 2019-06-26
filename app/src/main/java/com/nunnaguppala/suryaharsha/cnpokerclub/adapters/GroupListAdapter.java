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
import com.nunnaguppala.suryaharsha.cnpokerclub.database.entities.GroupEntity;

import java.util.List;

public class GroupListAdapter extends ArrayAdapter<GroupEntity> {

    private final LayoutInflater mInflater;

    public GroupListAdapter(Context context){
        super(context, R.layout.group_selector_list_item);
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<GroupEntity> listGroups, boolean clear) {
        if (clear) {
            clear();
        }
        if (listGroups != null) {
                addAll(listGroups);
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
            view = mInflater.inflate(R.layout.group_selector_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder((TextView) view.findViewById(android.R.id.text1));
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        TextView textView = holder.textView;

        GroupEntity group = getItem(position);
        SpannableStringBuilder builder = new SpannableStringBuilder(group.getName());
        builder.setSpan(new TextAppearanceSpan(getContext(), android.R.style.TextAppearance_DeviceDefault_Large),
                0, group.getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
