package com.example.namikkaya.lottoservicetr_android.model;

public class dbData {
    private String id;
    private String numbers;

    public dbData(String id, String numbers) {
        this.id = id;
        this.numbers = numbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
