package com.example.bryan.hereheroemergency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ListNumbersAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ListNumbers> arrayListNumbers;
    private LayoutInflater inflater;
    private boolean isListView;
    private int selectedPosition = -1;

    public ListNumbersAdapter(Context context, ArrayList<ListNumbers> arrayListNumbers, boolean isListView) {
        this.context = context;
        this.arrayListNumbers = arrayListNumbers;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return arrayListNumbers.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        final ListNumbers listNumbers = new ListNumbers();
        if (view == null) {
            viewHolder = new ViewHolder();
                view = inflater.inflate(R.layout.list_custom_row_layout, viewGroup, false);


            viewHolder.country = (TextView) view.findViewById(R.id.textView_ListNumberLayout_Country);
            viewHolder.number = (TextView) view.findViewById(R.id.textView_ListNumberLayout_Number);


            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.country.setText(listNumbers.getCountry());
        viewHolder.country.setTag(i);

        viewHolder.number.setText(listNumbers.getCountry());
        viewHolder.number.setTag(i);

        return view;
    }
    private class ViewHolder {
        private TextView country;
        private TextView number;

    }

}