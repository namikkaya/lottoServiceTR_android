package com.example.namikkaya.lottoservicetr_android.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.namikkaya.lottoservicetr_android.R;
import com.example.namikkaya.lottoservicetr_android.model.dbData;

import java.util.List;

public class myNumberAdapter extends BaseAdapter {
    private Context mcontext;
    private List<dbData> dbDataList;

    public myNumberAdapter(Context mcontext, List<dbData> dbDataList) {
        this.mcontext = mcontext;
        this.dbDataList = dbDataList;
    }


    @Override
    public int getCount() {
        return dbDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dbDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mcontext, R.layout.cell_myluckynumber,null);
        TextView text = view.findViewById(R.id.cellTitle);
        text.setText(dbDataList.get(position).getNumbers());
        return view;
    }
}
