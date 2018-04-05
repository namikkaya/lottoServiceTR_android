package com.example.namikkaya.lottoservicetr_android.model;


public class lottoResultData {
    private String rakamlarNumaraSirasi;
    private String rakamlar;
    private Double buyukIkramiye;


    public lottoResultData(String rakamlarNumaraSirasi, String rakamlar, Double buyukIkramiye) {
        this.rakamlarNumaraSirasi = rakamlarNumaraSirasi;
        this.rakamlar = rakamlar;
        this.buyukIkramiye = buyukIkramiye;
    }

    /**
     * @return Sıralı şanslı sayıları döndürür
     */
    public String getRakamlarNumaraSirasi() {
        return rakamlarNumaraSirasi;
    }

    /**
     *
     * @param rakamlarNumaraSirasi Sıralı hali ile string ekler
     */
    public void setRakamlarNumaraSirasi(String rakamlarNumaraSirasi) {
        this.rakamlarNumaraSirasi = rakamlarNumaraSirasi;
    }

    /**
     * @return Sırasız şanslı sayıları döndürür
     */
    public String getRakamlar() {
        return rakamlar;
    }

    /**
     * @param rakamlar Sırasız şekilde rakamları set eder
     */
    public void setRakamlar(String rakamlar) {
        this.rakamlar = rakamlar;
    }

    /**
     * @return büyük ikramiye tutarı döndürür
     */
    public Double getBuyukIkramiye() {
        return buyukIkramiye;
    }

    /**
     * @param buyukIkramiye tutarı set eder.
     */
    public void setBuyukIkramiye(Double buyukIkramiye) {
        this.buyukIkramiye = buyukIkramiye;
    }
}
