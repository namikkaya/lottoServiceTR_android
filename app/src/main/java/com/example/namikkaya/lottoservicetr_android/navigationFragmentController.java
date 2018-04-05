package com.example.namikkaya.lottoservicetr_android;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.namikkaya.lottoservicetr_android.Fragment.checkFragment;
import com.example.namikkaya.lottoservicetr_android.Fragment.dateListFragment;
import com.example.namikkaya.lottoservicetr_android.Fragment.mainPageFragment;
import com.example.namikkaya.lottoservicetr_android.model.dateData;
import com.example.namikkaya.lottoservicetr_android.model.lottoType;

public final class navigationFragmentController {

    private MainActivity context;
    // tarih bilgisini tutar
    private dateData selectedDateDataHolder;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public Context getContext() {
        return context;
    }

    public void setContext(MainActivity context) {
        this.context = context;
        fragmentManager = this.context.getSupportFragmentManager();
    }

    public dateData getSelectedDateDataHolder() {
        return selectedDateDataHolder;
    }

    public void setSelectedDateDataHolder(dateData selectedDateDataHolder) {
        this.selectedDateDataHolder = selectedDateDataHolder;
    }

    /**
     * Sayfa numarası yollandığında önceden tanımlanmış sayfa ismi set edilir.
     * 0 => Sayısal Loto
     * 1 => Süper Loto
     * @param currentPage
     */
    public void setNavigationTitle(int currentPage){
        if(currentPage == 0){
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            context.getSupportActionBar().setDisplayShowHomeEnabled(false);
            context.getSupportActionBar().setTitle(R.string.loto_sonuclari);
        }else if(currentPage == 1){
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            context.getSupportActionBar().setDisplayShowHomeEnabled(true);
            context.getSupportActionBar().setTitle(lottoType.getLottoTypeName() +" " + context.getResources().getString(R.string.loto_date));
        }else if (currentPage == 2) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            context.getSupportActionBar().setDisplayShowHomeEnabled(true);
            context.getSupportActionBar().setTitle(lottoType.getLottoTypeName() +" " + context.getResources().getString(R.string.loto_result));
        }
    }

    /**
     * Fragment
     *  0=> mainPage
     *  1=> dateList
     *  2=> checkFragment
     * @param pageType sayfa numarası temsil eder
     */
    public void fragmentViewController(int pageType){
        Fragment fragment = null;
        if (pageType == 0){ // ana sayfa
            fragment = mainPageFragment.newInstance();
        }else if(pageType == 1){ // liste sayfası
            fragment = dateListFragment.newInstance("dateList");
        }else if (pageType == 2){ // sonuç sayfası
            fragment = checkFragment.newInstance(getSelectedDateDataHolder());
        }

        // title set ediliyor
        this.setNavigationTitle(pageType);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right);
        fragmentTransaction.add(R.id.fragmentContainer,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public int getFragmentCount(){
        return fragmentManager.getBackStackEntryCount();
    }

}



