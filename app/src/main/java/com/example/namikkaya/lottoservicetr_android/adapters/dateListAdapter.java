package com.example.namikkaya.lottoservicetr_android.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.namikkaya.lottoservicetr_android.R;
import com.example.namikkaya.lottoservicetr_android.model.dateData;
import java.util.List;

public class dateListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<dateData> dateDataList;

    public dateListAdapter(Context mcontext, List<dateData> dateDataList) {
        this.mcontext = mcontext;
        this.dateDataList = dateDataList;
    }

    @Override
    public int getCount() {
        return dateDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dateDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mcontext, R.layout.cell,null);
        TextView text = view.findViewById(R.id.cellTitle);
        text.setText(dateDataList.get(position).getTarihView());
        return view;
    }
}
