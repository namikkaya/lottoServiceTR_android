package com.example.namikkaya.lottoservicetr_android.model;

import java.io.Serializable;

/**
 * Tarih ve tarih görünüm bilgisini tutar
 */
public class dateData implements Serializable{
    private String tarih;
    private String tarihView;

    public dateData(String tarih, String tarihView) {
        this.tarih = tarih;
        this.tarihView = tarihView;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getTarihView() {
        return tarihView;
    }

    public void setTarihView(String tarihView) {
        this.tarihView = tarihView;
    }
}
