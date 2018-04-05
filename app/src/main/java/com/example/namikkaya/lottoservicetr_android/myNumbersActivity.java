package com.example.namikkaya.lottoservicetr_android;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.namikkaya.lottoservicetr_android.adapters.myNumberAdapter;
import com.example.namikkaya.lottoservicetr_android.dialog.simpleDialog;
import com.example.namikkaya.lottoservicetr_android.model.dbData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class myNumbersActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, simpleDialog.simpleDialogDelegate {
    private static final String TAG = "myNumbersActivity";
    private Toolbar toolbar;
    private dbManager db;

    private ListView listView;
    private myNumberAdapter adapter;
    private List<dbData> dbList;

    private String selectedCellId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_numbers);

        db = new dbManager(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.custom_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.sansli_sayilarim);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        dbList = new ArrayList<dbData>();
        adapter = new myNumberAdapter(this,dbList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(this);

        getDBData();
    }

    private void getDBData(){
        ArrayList<HashMap<String, String>> numberList = new ArrayList<HashMap<String, String>>();
        numberList = db.getAllLuckyNumbers();
        for (int i=0; i<numberList.size(); i++){
            HashMap<String,String> item = numberList.get(i);
            dbData dbItem = new dbData(item.get("id"), item.get("numbers"));
            dbList.add(dbItem);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db = null;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        selectedCellId = dbList.get(position).getId();
        openSimpleDialog(dbList.get(position).getNumbers());
        return false;
    }

    public void openSimpleDialog(String number){
        simpleDialog dialog = simpleDialog.newInstance(number);
        dialog.show(getSupportFragmentManager(),"dialog");
    }

    //-----------------------------------------------------simpleDialogDelegate
    @Override
    public void positiveButtonEvent() {
        int getID = 0;
        try {
            getID = Integer.parseInt(this.selectedCellId);
            db.deleteLuckyNumbers(getID);
            dbList.removeAll(dbList);
            adapter.notifyDataSetChanged();
            this.getDBData();
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
    }

    @Override
    public void negativeButtonEvent() {
        //Log.d(TAG, "negativeButtonEvent: ");
    }
}
