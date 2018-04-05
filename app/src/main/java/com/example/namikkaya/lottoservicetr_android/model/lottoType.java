package com.example.namikkaya.lottoservicetr_android.model;


/**
 *
 * 0 => sayısal loto
 * 1 => süper loto
 *
 * getLottoTypeName => isim döner
 *
 */
public final class lottoType {
    private static String lottoTypeNameHolder = "Sayısal Loto";
    private final String sayisalLoto = "Sayısal Loto";
    private final String superLoto = "Süper Loto";
    /**
     * Anlık kaydedilmiş lotonun type ismini döner
     * @return
     */
    public static String getLottoTypeName() {
        return lottoTypeNameHolder;
    }

    private static int lottoTypeHolder = 0;

    /**
     * Loto tipini döndürür.
     * @return
     */
    public static int getLottoTypeHolder() {
        return lottoTypeHolder;
    }

    /**
     * Loto tipini değiştirir
     * 0=> Sayisal
     * 1=> Süper
     * @param lottoTypeHolder
     */
    public static void setLottoTypeHolder(int lottoTypeHolder) {
        lottoType.lottoTypeHolder = lottoTypeHolder;
        switch(lottoTypeHolder) {
            case 0 :
                lottoTypeNameHolder = "Sayısal Loto";
                break;
            case 1 :
                lottoTypeNameHolder = "Süper Loto";
                break;
        }
    }
}
