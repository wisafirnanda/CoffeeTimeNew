package com.coffeetime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Kopi implements Serializable {

    @SerializedName("id_kopi")
    @Expose
    private String idKopi;
    @SerializedName("nama_kopi")
    @Expose
    private String namaKopi;
    @SerializedName("harga_kopi")
    @Expose
    private String hargaKopi;
    @SerializedName("jenis_kopi")
    @Expose
    private String JenisKopi;
    @SerializedName("id_warkop")
    @Expose
    private String idWarkop;

    @SerializedName("qty")
    @Expose
    private String qty;

    @SerializedName("pos")
    @Expose
    private int pos;

    public Kopi() {
    }

    public Kopi(String idKopi, String hargaKopi, String qty) {
        this.idKopi = idKopi;
        this.hargaKopi = hargaKopi;
        this.qty = qty;
    }

    public String getIdKopi() {
        return idKopi;
    }

    public void setIdKopi(String idKopi) {
        this.idKopi = idKopi;
    }

    public String getNamaKopi() {
        return namaKopi;
    }

    public void setNamaKopi(String namaKopi) {
        this.namaKopi = namaKopi;
    }

    public String getHargaKopi() {
        return hargaKopi;
    }

    public void setHargaKopi(String hargaKopi) {
        this.hargaKopi = hargaKopi;
    }

    public String getJenisKopi() {
        return JenisKopi;
    }

    public void setJenisKopi(String JenisKopi) {
        this.JenisKopi = JenisKopi;
    }

    public String getIdWarkop() {
        return idWarkop;
    }

    public void setIdWarkop(String idWarkop) {
        this.idWarkop = idWarkop;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}