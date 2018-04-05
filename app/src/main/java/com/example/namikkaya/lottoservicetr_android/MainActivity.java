package com.example.namikkaya.lottoservicetr_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.namikkaya.lottoservicetr_android.Fragment.checkFragment;
import com.example.namikkaya.lottoservicetr_android.Fragment.dateListFragment;
import com.example.namikkaya.lottoservicetr_android.Fragment.mainPageFragment;
import com.example.namikkaya.lottoservicetr_android.model.dateData;
import com.example.namikkaya.lottoservicetr_android.model.lottoType;

public class MainActivity extends AppCompatActivity implements
        dateListFragment.dateListDelegate,
        mainPageFragment.mainPageDelegate,
        checkFragment.checkFragmentDelegate
{
    private final String TAG = "MainActivity";

    private dbManager db;
    private Toolbar toolbar;

    // Fragment Controller
    private navigationFragmentController nvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new dbManager(getApplicationContext());

        // Fragment sayfa navigasyon kontrolü
        nvc = new navigationFragmentController();
        nvc.setContext(this);

        // Toolbar/ActionBar
        toolbar = (Toolbar) findViewById(R.id.custom_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.loto_sonuclari);

        // ilk sayfa mainPage fragment gösterir.
        nvc.fragmentViewController(0);
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
        int count = nvc.getFragmentCount();
        if (count > 1) {
            nvc.setNavigationTitle(count-2);
            super.onBackPressed();
            return;
        }
    }

    //++
    //----------------------------------------------------dateList Delegate
    @Override
    public void selectedDateData(dateData item) {
        nvc.setSelectedDateDataHolder(item);
        nvc.fragmentViewController(2);
    }
    //----------------------------------------------------dateList Delegate Finish --


    //++
    //----------------------------------------------------mainPage Delegate
    @Override
    public void mainPageSelectedTypeLotto(int lType) {
        lottoType.setLottoTypeHolder(lType);
        nvc.fragmentViewController(1);
    }

    // şanslı sayılar sayfasına yollar
    @Override
    public void mainPageGoMyLuckyNumbers() {
        if (db.getRowCount() < 1){
            Toast toast= Toast.makeText(this,
                    "Kaydedilmiş numaralarınız yok.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, myNumbersActivity.class);
        startActivity(intent);
    }

    //----------------------------------------------------mainPage Delegate --

    //++
    //----------------------------------------------------checkFragment Delegate
    @Override
    public void checkFragmentSaveDB(String numbers) {
        db.insertLuckyNumber(numbers);
        onBackPressed();
    }
    //----------------------------------------------------checkFragment Delegate Finish --
}
