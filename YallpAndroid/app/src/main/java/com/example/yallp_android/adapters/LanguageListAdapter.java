package com.example.yallp_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Language;

import java.util.ArrayList;

public class LanguageListAdapter extends BaseAdapter {

    private ArrayList<Language> listData;
    private LayoutInflater layoutInflater;
    public LanguageListAdapter(Context aContext, ArrayList<Language> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;

        if (v == null) {
            v = layoutInflater.inflate(R.layout.languale_list_row, null);
            holder = new ViewHolder();
            holder.languageName = (TextView) v.findViewById(R.id.language);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        holder.languageName.setText(listData.get(position).getLanguageName());
        return v;
    }
    static class ViewHolder {
        TextView languageName;
    }
}
