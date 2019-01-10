package com.example.amarhumaki.autoservis.data;

import java.io.Serializable;
import java.util.List;

public class UpitiResultVM implements Serializable{

    public static class Row implements Serializable {
        public int UpitID;
        public String MarkaAuta; // atribut marka je i za model i marku
        public String KlijentID;
        public String DatumSlanja;
    }
    public List<Row> rows;

}
